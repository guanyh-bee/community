package com.gyh.community.provider;



import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.UUID;

@Component
public class UploadProvider {





    ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(
            "TOKEN_1176b46c-17e2-432e-b4c7-f8d7db3f56a7", "c7df648d-3cd9-4ad6-8af2-de3bfb66b811");


    ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");


    public String file(InputStream inputStream,Integer length,String mimeType,String fileName){
        String[] split = fileName.split("\\.");
        String name = UUID.randomUUID()+"."+split[split.length-1];

        try {
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(inputStream,length, mimeType)
                    .nameAs(name)
                    .toBucket("lmm")
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();

                    if( response != null && response.getRetCode() == 0 ){
                        String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                                .getDownloadUrlFromPrivateBucket(name, "lmm", 60*60*24*365*10)
                                /**
                                 * 使用Content-Disposition: attachment，并且默认文件名为KeyName
                                 */
//                    .withAttachment()
                                /**
                                 * 使用Content-Disposition: attachment，并且配置文件名
                                 */
//                    .withAttachment("filename")
                                /**
                                 * 图片处理服务
                                 * https://docs.ucloud.cn/ufile/service/pic
                                 */
//                    .withIopCmd("iopcmd=rotate&degree=0&type=1&text=lmm")
                                .createUrl();
                                return url;
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
                    }

        } catch (UfileClientException e) {
            e.printStackTrace();
        } catch (UfileServerException e) {
            e.printStackTrace();
        }
        return null;

    }
}
