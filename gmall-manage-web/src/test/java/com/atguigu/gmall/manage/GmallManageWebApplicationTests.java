package com.atguigu.gmall.manage;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws  Exception{
        // 获得配置文件的路径
        String tracker = GmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient  = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();

        StorageClient storageClient = new StorageClient(trackerServer, null);

        String[] uploadInfos = storageClient.upload_file("d:/a.jpg", "jpg", null);

        String url = "http://192.168.1.58";

        for (String uploadInfo : uploadInfos) {
            url += "/"+uploadInfo;

            //url = url + uploadInfo;
        }

        System.out.println(url);

//        String[] uploads = storageClient.upload_file("C:/Users/Administrator/Desktop/luohaobo.jpg",
//                "jpg", null);

//
//
//        for (String upload : uploads) {
//            System.out.println("---------upload:"+upload);
//
//        }


    }

}
