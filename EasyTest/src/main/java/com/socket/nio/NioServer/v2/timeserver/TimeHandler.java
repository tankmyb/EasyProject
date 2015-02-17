package com.socket.nio.NioServer.v2.timeserver;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.socket.nio.NioServer.v2.nioserver.Request;
import com.socket.nio.NioServer.v2.nioserver.Response;
import com.socket.nio.NioServer.v2.nioserver.event.EventAdapter;

/**
 * 时间查询服务器
 */
public class TimeHandler extends EventAdapter {
    public TimeHandler() {
    }

    public void onWrite(Request request, Response response) throws Exception {
        String command = new String(request.getDataInput());
        String time = null;
        Date date = new Date();

        // 判断查询命令
        if (command.equals("GB")) {
            // 中文格式
            DateFormat cnDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.CHINA);
            time = cnDate.format(date);
        }
        else {
            // 英文格式
            DateFormat enDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.US);
            time = enDate.format(date);
        }
        System.out.println(time+"=======");
        response.send(time.getBytes());
    }
}
