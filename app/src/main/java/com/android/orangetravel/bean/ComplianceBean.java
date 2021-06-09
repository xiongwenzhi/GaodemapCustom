package com.android.orangetravel.bean;

import com.android.orangetravel.base.bean.ErrorMsgBean;

import java.util.List;

/**
 * @author Mr Xiong
 * @date 2021/3/23
 */

public class ComplianceBean extends ErrorMsgBean {


    /**
     * compliance : 0
     * name : 成为合规司机
     * field : 早日合规，安心出车
     * tips : 通过网约车驾驶员证和网约车运输证，保证司机正常接单，更安心出车
     * question : https://dpubstatic.udache.com/static/dpubimg/18a8630ac4c611e3d81157b6a4e51896/index.html?pid=1_0YZ-GXil4
     * qualification : [{"id":"1","name":"网约车驾驶员证","tip":"上传证书到安橙平台,享受合规权益保障","type":"driving","upload":"0","guide":"查看网约车驾驶员证办理引导","guideUrl":"http://mobile.jxllhb.com/#/pages/problem/problem4"},{"id":"1","name":"网约车运输证","tip":"上传证书到安橙平台,享受合规权益保障","type":"transport","upload":"0","guide":" ","guideUrl":" "}]
     */

    private int compliance;
    private String name;
    private String field;
    private String tips;
    private String question;
    private List<QualificationBean> qualification;

    public int getCompliance() {
        return compliance;
    }

    public void setCompliance(int compliance) {
        this.compliance = compliance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<QualificationBean> getQualification() {
        return qualification;
    }

    public void setQualification(List<QualificationBean> qualification) {
        this.qualification = qualification;
    }

    public static class QualificationBean {
        /**
         * id : 1
         * name : 网约车驾驶员证
         * tip : 上传证书到安橙平台,享受合规权益保障
         * type : driving
         * upload : 0
         * guide : 查看网约车驾驶员证办理引导
         * guideUrl : http://mobile.jxllhb.com/#/pages/problem/problem4
         */

        private String id;
        private String name;
        private String tip;
        private String type;
        private String upload;
        private String guide;
        private String guideUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpload() {
            return upload;
        }

        public void setUpload(String upload) {
            this.upload = upload;
        }

        public String getGuide() {
            return guide;
        }

        public void setGuide(String guide) {
            this.guide = guide;
        }

        public String getGuideUrl() {
            return guideUrl;
        }

        public void setGuideUrl(String guideUrl) {
            this.guideUrl = guideUrl;
        }
    }
}
