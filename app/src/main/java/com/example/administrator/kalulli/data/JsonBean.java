package com.example.administrator.kalulli.data;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1.
 */

public class JsonBean {

    /**
     * log_id : 6900350655582457153
     * result_num : 1
     * result : [{"calorie":"331","has_calorie":true,"name":"慕斯","probability":"0.1239","baike_info":{"baike_url":"http://baike.baidu.com/item/%E6%85%95%E6%96%AF%E8%9B%8B%E7%B3%95/10639896","image_url":"http://imgsrc.baidu.com/baike/pic/item/ae51f3deb48f8c54f2afd39730292df5e0fe7f0d.jpg","description":"慕斯蛋糕是一种以慕斯粉为主材料的糕点。外型、色泽、结构、口味变化丰富，更加自然纯正，通常是加入奶油与凝固剂来制作成浓稠冻状的效果。慕斯的英文是mousse，是一种奶冻式的甜点，可以直接吃或做蛋糕夹层，通常是加入奶油与凝固剂来造成浓稠冻状的效果。慕斯是从法语音译过来的。慕斯蛋糕最早出现在美食之都法国巴黎，最初大师们在奶油中加入起稳定作用和改善结构，口感和风味的各种辅料，冷冻后食用其味无穷，成为蛋糕中的极品。慕斯与布丁一样属于甜点的一种，其性质较布丁更柔软，入口即化。"}}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * calorie : 331
         * has_calorie : true
         * name : 慕斯
         * probability : 0.1239
         * baike_info : {"baike_url":"http://baike.baidu.com/item/%E6%85%95%E6%96%AF%E8%9B%8B%E7%B3%95/10639896","image_url":"http://imgsrc.baidu.com/baike/pic/item/ae51f3deb48f8c54f2afd39730292df5e0fe7f0d.jpg","description":"慕斯蛋糕是一种以慕斯粉为主材料的糕点。外型、色泽、结构、口味变化丰富，更加自然纯正，通常是加入奶油与凝固剂来制作成浓稠冻状的效果。慕斯的英文是mousse，是一种奶冻式的甜点，可以直接吃或做蛋糕夹层，通常是加入奶油与凝固剂来造成浓稠冻状的效果。慕斯是从法语音译过来的。慕斯蛋糕最早出现在美食之都法国巴黎，最初大师们在奶油中加入起稳定作用和改善结构，口感和风味的各种辅料，冷冻后食用其味无穷，成为蛋糕中的极品。慕斯与布丁一样属于甜点的一种，其性质较布丁更柔软，入口即化。"}
         */

        private String calorie;
        private boolean has_calorie;
        private String name;
        private String probability;
        private BaikeInfoBean baike_info;

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public boolean isHas_calorie() {
            return has_calorie;
        }

        public void setHas_calorie(boolean has_calorie) {
            this.has_calorie = has_calorie;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProbability() {
            return probability;
        }

        public void setProbability(String probability) {
            this.probability = probability;
        }

        public BaikeInfoBean getBaike_info() {
            return baike_info;
        }

        public void setBaike_info(BaikeInfoBean baike_info) {
            this.baike_info = baike_info;
        }

        public static class BaikeInfoBean {
            /**
             * baike_url : http://baike.baidu.com/item/%E6%85%95%E6%96%AF%E8%9B%8B%E7%B3%95/10639896
             * image_url : http://imgsrc.baidu.com/baike/pic/item/ae51f3deb48f8c54f2afd39730292df5e0fe7f0d.jpg
             * description : 慕斯蛋糕是一种以慕斯粉为主材料的糕点。外型、色泽、结构、口味变化丰富，更加自然纯正，通常是加入奶油与凝固剂来制作成浓稠冻状的效果。慕斯的英文是mousse，是一种奶冻式的甜点，可以直接吃或做蛋糕夹层，通常是加入奶油与凝固剂来造成浓稠冻状的效果。慕斯是从法语音译过来的。慕斯蛋糕最早出现在美食之都法国巴黎，最初大师们在奶油中加入起稳定作用和改善结构，口感和风味的各种辅料，冷冻后食用其味无穷，成为蛋糕中的极品。慕斯与布丁一样属于甜点的一种，其性质较布丁更柔软，入口即化。
             */

            private String baike_url;
            private String image_url;
            private String description;

            public String getBaike_url() {
                return baike_url;
            }

            public void setBaike_url(String baike_url) {
                this.baike_url = baike_url;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
