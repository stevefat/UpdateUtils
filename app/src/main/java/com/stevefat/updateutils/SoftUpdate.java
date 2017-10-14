package com.stevefat.updateutils;
/**
 * Created by stevefat on 17-10-11.
 */

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-11 上午9:39
 */
public class SoftUpdate {


    /**
     * status : success
     * softupdate : {"name":"ZZReaderLite.v2.1.0.apk","version":"2.1.0","url":"http://book.gisroad.com/dl/ZZReaderLite.v2.1.0.apk","content":"<p>1、新增下载废止版本<\/p> <p>2、新增智能与精确搜索关键字高亮<\/p> <p>3、优化书籍下载<\/p> <p>4、优化软件稳定性<\/p>"}
     */

    private String status;
    private SoftupdateBean softupdate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SoftupdateBean getSoftupdate() {
        return softupdate;
    }

    public void setSoftupdate(SoftupdateBean softupdate) {
        this.softupdate = softupdate;
    }

    public static class SoftupdateBean {
        /**
         * name : ZZReaderLite.v2.1.0.apk
         * version : 2.1.0
         * url : http://book.gisroad.com/dl/ZZReaderLite.v2.1.0.apk
         * content : <p>1、新增下载废止版本</p> <p>2、新增智能与精确搜索关键字高亮</p> <p>3、优化书籍下载</p> <p>4、优化软件稳定性</p>
         */

        private String name;
        private String version;
        private String url;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
