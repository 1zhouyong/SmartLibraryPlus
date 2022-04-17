package com.example.smartlibrary.bean;

import java.io.Serializable;

public class UseInfoBean {

    /**
     * user : {"id":1,"studyId":"1904011011","password":"f9ca93d02b7554210c932201348cfaba","name":"张三","identity":"342901199909043528","type":"1","classes":null,"instructor":null,"headPic":"/Users/apple/Desktop/libary/web/src/main/resources/static/headpic/1.jpeg","headPicture":null,"phone":null,"age":null,"sex":null,"descb":"张三老师被评为2019级优秀教师","intake":null,"address":null,"deleteFlag":"0","createdBy":"system","createdAt":"2020-04-05T11:21:11.000+0800","updatedBy":"system","updatedAt":"2020-04-05T11:21:11.000+0800"}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * id : 1
         * studyId : 1904011011
         * password : f9ca93d02b7554210c932201348cfaba
         * name : 张三
         * identity : 342901199909043528
         * type : 1
         * classes : null
         * instructor : null
         * headPic : /Users/apple/Desktop/libary/web/src/main/resources/static/headpic/1.jpeg
         * headPicture : null
         * phone : null
         * age : null
         * sex : null
         * descb : 张三老师被评为2019级优秀教师
         * intake : null
         * address : null
         * deleteFlag : 0
         * createdBy : system
         * createdAt : 2020-04-05T11:21:11.000+0800
         * updatedBy : system
         * updatedAt : 2020-04-05T11:21:11.000+0800
         */

        private int id;
        private String studyId;
        private String password;
        private String name;
        private String identity;
        private String type;
        private String classes;
        private String instructor;
        private String headPic;
        private String headPicture;
        private String phone;
        private String age;
        private String sex;
        private String descb;
        private String intake;
        private String address;
        private String deleteFlag;
        private String createdBy;
        private String createdAt;
        private String updatedBy;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStudyId() {
            return studyId;
        }

        public void setStudyId(String studyId) {
            this.studyId = studyId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getInstructor() {
            return instructor;
        }

        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDescb() {
            return descb;
        }

        public void setDescb(String descb) {
            this.descb = descb;
        }

        public String getIntake() {
            return intake;
        }

        public void setIntake(String intake) {
            this.intake = intake;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
