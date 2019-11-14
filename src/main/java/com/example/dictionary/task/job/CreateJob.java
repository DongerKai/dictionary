package com.example.dictionary.task.job;

import com.example.dictionary.model.dataobject.UserDo;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Component
@AllArgsConstructor
@Slf4j
public class CreateJob {
    private static final int THRESHOLD = 100;
    private static final int POOL_SIZE = 4;
    private static final int BATCH_SIZE = 1000;
    private static ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
    private UserService userService;

    public void doWork(){
        try {
            batch();
        }catch (Exception e){

        }
    }

    private void batch(){
        List<UserDo> userDoList = new ArrayList<>();
        for (int i = 1; true; i++){
            List<UserDo> userList = userService.qryUserList(i, BATCH_SIZE).getList();
            if (CollectionUtils.isEmpty(userList))
                break;
            ForkJoinTask<List<UserDo>> batchAction = new BatchAction(userList, 0, userList.size());
            List<UserDo> tempList = pool.invoke(batchAction);
        }
    }

    @AllArgsConstructor
    private class BatchAction extends RecursiveTask<List<UserDo>> {
        private List<UserDo> userList;
        private int start;
        private int end;
        @Override
        protected List<UserDo> compute() {
            if (end -start <= THRESHOLD) {
                List<UserDo> temp = userList.subList(start, end);
                temp.forEach(row->{
                    try {
                        doAction(row);
                    }catch (Exception e){
                        log.error("CreateJob BatchAction compute error:{}, msg:{}", row.toString(), e.getMessage());
                    }
                });
            }
            int middle = (end + start) / 2;
            BatchAction subtask1 = new BatchAction(userList, start, middle);
            BatchAction subtask2 = new BatchAction(userList, middle, end);
            invokeAll(subtask1, subtask2);
            List<UserDo> list1 = subtask1.join();
            List<UserDo> list2 = subtask2.join();
            list1.addAll(list2);
            return list1;
        }

        private void doAction(UserDo user){
            //do action
        }
    }
}
