<template>
  <div>
    <el-row>
      <el-col>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{path:'/home'}">指标管理</el-breadcrumb-item>
          <el-breadcrumb-item>指标管理</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <div>
      <el-row class="query-form">
        <el-col :span="6" :offset="1">
          <el-input
            label="SystemId*"
            v-model="currentValue"
            placeholder="请输系统ID"
            @change="onSearch"
          ></el-input>
        </el-col>
        <el-col :span="2" :offset="1">
          <el-button type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-button type="primary" fixed="right" @click="handleAdd" class="add_button">添加</el-button>
      </el-row>
    </div>
    <br>

    <div>
      <el-table :data="metricList" style="width: 100%" empty-text="暂无数据" border fit>
        <template scope="scope">
          <el-table-column label="指标ID" prop="mId" align="center"></el-table-column>
          <el-table-column label="指标名称" prop="title" align="center" width="200"></el-table-column>
          <el-table-column label="指标" prop="metric" align="center" width="260"></el-table-column>
          <el-table-column label="表格类型" prop="chart" align="center" :formatter="formatterChart"></el-table-column>
          <el-table-column label="函数" prop="aggFun" align="center"></el-table-column>
          <el-table-column label="标签" prop="tags" align="center" :formatter="formatterTags"></el-table-column>
          <el-table-column label="操作" align="center">
            <template scope="scope">
              <el-button @click="handleDelete(scope)" size="small" type="warning">删除</el-button>
            </template>
          </el-table-column>
        </template>
      </el-table>
    </div>

    <el-dialog
      title="创建指标"
      :visible.sync="dialogVisible_addMetric"
      size="tiny"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="metric" label-width="100px">
        <el-form-item label="SystemId*">
          <el-input style="width:300px" v-model="metric.sId"></el-input>
        </el-form-item>
        <el-form-item label="指标名称*">
          <el-input style="width:300px" v-model="metric.title"></el-input>
        </el-form-item>
        <el-form-item label="指标*">
          <el-input style="width:300px" v-model="metric.metric"></el-input>
        </el-form-item>
        <el-form-item label="图表类型*">
          <el-select style="width:300px" v-model="metric.chart" placeholder="请选择图表类型">
            <el-option label="single图" value="single"></el-option>
            <el-option label="柱状图" value="histogram"></el-option>
            <el-option label="折线图" value="line"></el-option>
            <el-option label="饼图" value="pie"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="函数*">
          <el-select style="width:300px" v-model="metric.func" placeholder="请选择函数类型">
            <el-option label="min" value="min"></el-option>
            <el-option label="max" value="max"></el-option>
            <el-option label="average" value="average"></el-option>
            <el-option label="standardDeviation" value="standardDeviation"></el-option>
            <el-option label="sum" value="sum"></el-option>
            <el-option label="count" value="count"></el-option>
            <el-option label="last" value="last"></el-option>
            <el-option label="first" value="first"></el-option>
            <el-option label="gaps" value="gaps"></el-option>
            <el-option label="leastSquares" value="leastSquares"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签*">
          <el-input type="textarea" style="width:300px" v-model="metric.tagsStr"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addMetric">提交</el-button>
          <el-button @click="onClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="请确认是否删除该指标！" :visible.sync="dialogVisible_deleteMetric" size="tiny">
      <el-form ref="form" :model="metric" label-width="100px">
        <el-form-item label="指标ID">
          <el-input :disabled="true" style="width:350px" v-model="metric.mId"></el-input>
        </el-form-item>
        <el-form-item label="指标名称">
          <el-input :disabled="true" style="width:350px" v-model="metric.title"></el-input>
        </el-form-item>
        <el-form-item label="指标">
          <el-input :disabled="true" style="width:350px" v-model="metric.metric"></el-input>
        </el-form-item>
        <el-form-item label="图标类型">
          <el-input :disabled="true" style="width:350px" v-model="metric.chart"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="deleteMetric">确认</el-button>
          <el-button @click="onClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  methods: {
    handleView() {},
    handleEdit(data) {
      this.spec = data.row;
      this.dialogVisible = true;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.$store.dispatch("queryMetric", {
        pageSize: this.pageSize,
        page: this.currentPage,
        sId: this.currentValue
      });
    },
    handleCurrentChange() {
      this.$store.dispatch("queryMetric", {
        pageSize: this.pageSize,
        page: this.currentPage,
        sId: this.currentValue
      });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleAdd() {
      this.metric = {
        mId: null
      };
      this.dialogVisible_addMetric = true;
    },
    addMetric() {
      this.dialogVisible_addMetric = false;
      if (this.metric.tagsStr) {
        this.metric.tagName = JSON.parse(this.metric.tagsStr);
      }
      this.$store.dispatch("addMetric", {
        metric: this.metric,
        query: {
          pageSize: this.pageSize,
          page: this.currentPage,
          sId: this.currentValue
        }
      });
    },
    handleDelete(data) {
      this.metric = data.row;
      this.dialogVisible_deleteMetric = true;
    },
    deleteMetric() {
      this.dialogVisible_deleteMetric = false;
      this.$store.dispatch("deleteMetric", {
        id: this.metric.mId,
        query: {
          pageSize: this.pageSize,
          page: this.currentPage,
          sId: this.currentValue
        }
      });
    },
    onClose() {
      this.dialogVisible_addMetric = false;
    },
    onSearch() {
      // if (!this.currentValue) {
      //   this.$message.error("请输入需要查询的系统ID");
      //   return;
      // }
      this.$store.dispatch("queryMetric", {
        pageSize: this.pageSize,
        page: this.currentPage,
        sId: this.currentValue
      });
    },
    formatterTags(row, column) {
      if (row.tags) {
        return JSON.stringify(row.tags);
      }
      return "";
    },
    formatterChart(row, column) {
      if (row.chart == "single") {
        return "single图";
      } else if (row.chart == "pie") {
        return "饼图";
      } else if (row.chart == "line") {
        return "折线图";
      } else if (row.chart == "histogram") {
        return "柱状图";
      } else {
        return row.chart;
      }
    }
  },

  data: function() {
    return {
      dialogVisible: false,
      dialogVisible_addMetric: false,
      dialogVisible_deleteMetric: false,
      currentField: {
        value: "sId",
        label: "SystemId"
      },
      currentPage: 1,
      pageSize: 10,
      currentValue: "",
      metric: {
        mId: "",
        title: "",
        sId: "",
        metric: "",
        chart: "",
        func: "",
        tagsStr: "",
        tagName: {}
      }
    };
  },
  computed: {
    ...mapGetters({
      metricList: "getMetricList",
      fieldList: "getMetricFieldList",
      total: "getMetricTotal",
      isLogin: "getLoginState",
      promptMessage: "getPromptMessage"
    })
  },
  created() {
    var payload = null
    if(this.$route.query.sId != undefined){
        payload = {
            sId: this.$route.query.sId,
            pageSize: this.pageSize,
            page: this.currentPage
        };
    }
    this.$store.dispatch('queryMetric', payload);
  },
  watch: {
    promptMessage: function(newMessage) {
      if (newMessage.success) {
        this.$message.success("操作成功");
      } else {
        this.$message.error(newMessage.message);
      }
    }
  }
};
</script>

<style>
.add_button {
  float: right;
}

.demo-table-expand {
  font-size: 0;
  padding: 20px 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

