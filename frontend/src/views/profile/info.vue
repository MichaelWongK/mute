<template>
  <div class="app-container">
    <div class="components-container">
      <pan-thumb :image="image" />

      <el-button
        type="primary"
        icon="el-icon-upload"
        style="position: absolute; bottom: 15px; margin-left: 40px"
        @click="toggleShow"
      >
        修改头像
      </el-button>

      <image-cropper
        v-model="show"
        field="multipartFile"
        :width="300"
        :height="300"
        :url="url"
        :params="params"
        :headers="headers"
        img-format="png"
        @crop-success="cropSuccess"
        @crop-upload-success="cropUploadSuccess"
        @crop-upload-fail="cropUploadFail"
      />
    </div>
    <el-form
      ref="form"
      v-loading="formLoading"
      :data="form"
      element-loading-text="加载中..."
      :model="form"
      label-width="120px"
    >
      <el-input v-model="form.id" type="hidden" />
      <el-form-item label="账号">
        <el-input v-model="form.username" :disabled="true" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickName" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.note" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="form.createTime" :disabled="true" />
      </el-form-item>
      <el-form-item label="最后登录">
        <el-input v-model="form.loginTime" :disabled="true" />
      </el-form-item>
      <el-form-item label="是否启用">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">启用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { info, update, modifyIcon } from '@/api/profile'
import PanThumb from '@/components/PanThumb'
import ImageCropper from 'vue-image-crop-upload'
import { getToken } from '@/utils/auth'

export default {
  name: "ProfileInfo",
  components: { PanThumb, ImageCropper },
  data() {
    return {
      url: process.env.VUE_APP_BASE_API + '/upload',
      formLoading: true,
      image: this.$store.getters.avatar,
      show: false,
      params: {
        access_token: getToken(),
      },
      headers: {
        smail: '*_~'
      },
      form: {
        id: '',
        username: '',
        email: '',
        nickName: '',
        note: '',
        createTime: '',
        loginTime: '',
        status: ''
      }
    };
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      info(this.$store.getters.name).then(response => {
        this.form = response.data
        this.formLoading = false
      })
    },

    onSubmit() {
      this.formLoading = true
      update(this.form).then(response => {
        this.formLoading = false
        this.$message({
          message: response.message,
          type: 'success'
        })
      }).catch(() => {
        this.formLoading = false
      })
    },

    // 修改头像
    toggleShow() {
      this.show = !this.show;
    },
    /**
    * crop success
    *
    * [param] image
    * [param] field
    */
    cropSuccess(image, field) {
      console.log('-------- crop success --------');
      this.image = image;
    },
    /**
       * upload success
       *
       * [param] jsonData  server api return data, already json encode
       * [param] field
       */
    cropUploadSuccess(jsonData, field) {
      console.log('-------- upload success --------');

      modifyIcon({
        username: this.form.username,
        path: jsonData.data.path
      }).then(response => {
        this.$message({
          message: response.message,
          type: "success"
        })

        // 更新 vuex 中的头像
        this.$store.dispatch('user/setAvatar', jsonData.data.path)
      }).catch(() => {
      })

      console.log(jsonData);
      console.log('field: ' + field);
    },
    /**
     * upload fail
     *
     * [param] status    server api return error status, like 500
     * [param] field
     */
    cropUploadFail(status, field) {
      console.log('-------- upload fail --------');
      console.log(status);
      console.log('field: ' + field);
    }
  }
};
</script>
