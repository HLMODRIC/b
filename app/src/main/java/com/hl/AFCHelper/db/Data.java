package com.hl.AFCHelper.db;

import java.io.Serializable;

/**
 * Created by huanglei on 2018/1/17.
 */

public class Data implements Serializable {
        private int mId;
        private String new_title;
        private String new_content;

        public Data(int mId,String new_title, String new_content) {
            this.new_title = new_title;
            this.new_content = new_content;
            this.mId = mId;
        }

        public String getNew_title() {
            return new_title;
        }

    public int getId() {
        return this.mId;
    }


        public String getNew_content() {
            return new_content;
        }

        public void setNew_title(String new_title) {
            this.new_title = new_title;
        }

        public void setNew_content(String new_content) {
            this.new_content = new_content;
        }
    }
