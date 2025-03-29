package com.lihui.security_office_backend.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lihui.security_office_backend.model.entity.User;
import com.lihui.security_office_backend.service.UserService;
import java.util.ArrayList;
import java.util.List;

public class UserExcelListener extends AnalysisEventListener<User> {

    private final UserService userService;
    private final List<User> userList = new ArrayList<>();

    public UserExcelListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void invoke(User user, AnalysisContext context) {
        userList.add(user);
        // 每500条保存一次，防止内存溢出
        if (userList.size() >= 500) {
            saveData();
            userList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        if (!userList.isEmpty()) {
            userService.saveBatch(userList);
        }
    }
}
