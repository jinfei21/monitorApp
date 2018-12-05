<template>
    <div>
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{path:'/home'}">系统管理</el-breadcrumb-item>
                    <el-breadcrumb-item>系统管理</el-breadcrumb-item>
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
            <el-table :data="systemList" style="width: 100%" empty-text="暂无数据" border fit>
                <template scope="scope">
                    <el-table-column label="系统ID" prop="id" align="center">
                        <template scope="props">
                            <router-link :to="{name: 'metric', query: { sId: props.row.id }}">{{props.row.id}}</router-link>
                        </template>
                    </el-table-column>
                    <el-table-column label="系统名称" prop="name" align="center" width="200"></el-table-column>
                    <el-table-column label="部门" prop="depart" align="center" width="260"></el-table-column>
                    <el-table-column label="负责人" prop="owner" align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template scope="scope">
                            <el-button @click="handleDelete(scope)" size="small" type="warning">删除</el-button>
                        </template>
                    </el-table-column>
                </template>
            </el-table>
        </div>

        <el-dialog
                title="创建系统"
                :visible.sync="dialogVisible_addSystem"
                size="tiny"
                :before-close="handleClose"
        >
            <el-form ref="form" :model="system" label-width="100px">
                <el-form-item label="系统名称*">
                    <el-input style="width:300px" v-model="system.name"></el-input>
                </el-form-item>
                <el-form-item label="部门*">
                    <el-input style="width:300px" v-model="system.depart"></el-input>
                </el-form-item>
                <el-form-item label="负责人*">
                    <el-input style="width:300px" v-model="system.owner"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addSystem">提交</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="请确认是否删除该系统！" :visible.sync="dialogVisible_deleteSystem" size="tiny">
            <el-form ref="form" :model="system" label-width="100px">
                <el-form-item label="系统ID">
                    <el-input :disabled="true" style="width:350px" v-model="system.id"></el-input>
                </el-form-item>
                <el-form-item label="系统名称">
                    <el-input :disabled="true" style="width:350px" v-model="system.name"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                    <el-input :disabled="true" style="width:350px" v-model="system.depart"></el-input>
                </el-form-item>
                <el-form-item label="负责人">
                    <el-input :disabled="true" style="width:350px" v-model="system.owner"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="deleteSystem">确认</el-button>
                    <el-button @click="onClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        methods: {
            handleView() {
            },
            handleEdit(data) {
                this.spec = data.row;
                this.dialogVisible = true;
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.currentPage = 1;
                this.$store.dispatch("querySystem", {
                    pageSize: this.pageSize,
                    page: this.currentPage,
                    sId: this.currentValue
                });
            },
            handleCurrentChange() {
                this.$store.dispatch("querySystem", {
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
                    .catch(_ => {
                    });
            },
            handleAdd() {
                this.system = {
                    mId: null
                };
                this.dialogVisible_addSystem = true;
            },
            addSystem() {
                this.dialogVisible_addSystem = false;
                this.$store.dispatch("addSystem", {
                    system: this.system,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        sId: this.currentValue
                    }
                });
            },
            handleDelete(data) {
                this.system = data.row;
                this.dialogVisible_deleteSystem = true;
            },
            deleteSystem() {
                this.dialogVisible_deleteSystem = false;
                this.$store.dispatch("deleteSystem", {
                    id: this.system.id,
                    query: {
                        pageSize: this.pageSize,
                        page: this.currentPage,
                        sId: this.currentValue
                    }
                });
            },
            onClose() {
                this.dialogVisible_addSystem = false;
            },
            onSearch() {
                // if (!this.currentValue) {
                //     this.$message.error("请输入需要查询的系统ID");
                //     return;
                // }
                this.$store.dispatch("querySystem", {
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

        data: function () {
            return {
                dialogVisible: false,
                dialogVisible_addSystem: false,
                dialogVisible_deleteSystem: false,
                currentField: {
                    value: "id",
                    label: "系统名称"
                },
                currentPage: 1,
                pageSize: 10,
                currentValue: "",
                system: {
                    mId: "",
                    title: "",
                    sId: "",
                    system: "",
                    chart: "",
                    func: "",
                    tagsStr: "",
                    tagName: {}
                }
            };
        },
        computed: {
            ...mapGetters({
                systemList: "getSystemList",
                fieldList: "getSystemFieldList",
                total: "getSystemTotal",
                isLogin: "getLoginState",
                promptMessage: "getPromptMessage"
            })
        },
        created() {
            this.$store.dispatch('querySystem');
        },
        watch: {
            promptMessage: function (newMessage) {
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

