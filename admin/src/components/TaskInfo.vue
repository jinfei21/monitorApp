<template>
    <div>    
        <div>
        	<el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{path:'/task'}">Task列表</el-breadcrumb-item>
                        <el-breadcrumb-item>Task详情</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>           
        </div>
        <br/>        
        <div>
         <el-row class="query-form">
                <el-col :span="3">
                  task状态：<el-button type="text">{{taskInfo.status}}</el-button>
                </el-col>
                <el-col :span="21" align="right">
                  <el-button v-if="taskInfo.status=='TASK_RUNNING'" type="danger" fixed="right" @click="stopTask" >停止</el-button>
                </el-col>
         </el-row>
        </div>
        <hr/>
        <div>
            Files:
            <el-table :data="taskFile" style="..." empty-text="暂无数据" border fit>
                <el-table-column label="Name" align="center" sortable>
                    <template scope="props">
                        <span v-if="props.row.name.indexOf('.jar')!=-1">{{props.row.name}}</span>
                        <a v-if="props.row.name.indexOf('.jar')==-1" style="cursor: pointer;" @click="view(props)">{{props.row.name}}</a>
                    </template>
                </el-table-column>
                <el-table-column label="Size" prop="size" :formatter="fr" align="center"></el-table-column>
                <el-table-column label="LastModify" prop="updateTime" align="center"></el-table-column>              
                 <el-table-column v-if="isLogin" label="操作" align="center" width="200">
                    <template scope="props">    
                     <label> 
                           <el-button  v-if="props.row.name.indexOf('jar')==-1" type="primary" size="small"  @click="view(props)">查看</el-button>        
                           <el-button type="primary" @click="download(props)" size="small">下载</el-button>                  
                       </label>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <hr/>
        Info:
        <div class="el-table__header-wrapper" > 
               <table class="tableFile" cellspacing="0" cellpadding="0" border="0" width="100%">
                <tr>
                    <td class="cell"> TaskID:</td>
                    <td>{{taskInfo.id}}</td>
                </tr>
                <tr>
                    <td class="cell">Host:</td>
                    <td>{{taskInfo.host}}</td>
                </tr>
                <tr>
                    <td class="cell">Director:</td>
                    <td>{{taskInfo.sandbox}}</td>
                </tr>
                <tr>
                    <td class="cell">SlaveId:</td>
                    <td>{{taskInfo.slaveId}}</td>
                </tr>
                <tr>
                    <td class="cell">RackId:</td>
                    <td>{{taskInfo.rackId}}</td>
                </tr>
                <tr>
                    <td class="cell">PackageType:</td>
                    <td>{{taskInfo.packageType}}</td>
                </tr>
            </table>                  
        </div>
         <hr/>
        <div>
            Resource Usage:
            <div class="el-table__header-wrapper" >
                <table class="tableFile" cellspacing="0" cellpadding="0" border="0" width="100%">

                    <tbody>
                    <tr>
                        <td>Name</td>
                        <td class="cell">Used</td>
                        <td class="cell">Allocated</td>
                        <td class="cell"> Total</td>
                    </tr>
                    <tr>
                        <td class="cell"> CPU</td>
                        <td>{{taskStatics.cpuUsed}}</td>
                        <td>{{taskResource.allocate.numCpus}}</td>
                        <td>{{taskResource.slave.numCpus}}</td>
                    </tr>

                    <tr>
                        <td class="cell"> Memory</td>
                        <td>{{(taskStatics.memRssBytes/1024/1024).toFixed(0)}}MB</td>
                        <td>{{taskResource.allocate.mem}}MB</td>
                        <td>{{taskResource.slave.mem}}MB</td>
                    </tr>
                    <tr>
                        <td class="cell"> Disk</td>
                        <td>N/A</td>
                        <td>{{taskResource.allocate.diskSpace}}MB</td>
                        <td>{{taskResource.slave.diskSpace}}MB</td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
		<el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                size="tiny"
                :before-close="onClose">
            <span>请确认是否停止该任务！</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onSubmit">确认</el-button>
                <el-button @click="onClose">取消</el-button>
            </span>
        </el-dialog>
    </div>
    
    
</template>

<script>
    import {mapGetters} from 'vuex';
    
    export default {
        methods: {   
            download(props) {                
			   window.location=props.row.downUrl;
            },view(props){
			    if(props.row.name.indexOf('.jar')==-1){
			        debugger;
                  var m ="/api/sandbox/"+ this.$store.getters.getTaskInfo.id+"/read?path="+props.row.path;
                  window.open("/pailer.html",m, 'height=500, width=500, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');
                }
                return false;
            },
            stopTask(){
			   this.dialogVisible = true;
			},
		    onSubmit() {
                this.dialogVisible = false;
                this.$store.dispatch('stopTaskInfo', {
                    taskId: this.$route.params.id
                });
                console.log(this.task)
            },
            onClose() {
                this.dialogVisible = false;
            },
            fr(row, column, cellValue) {
                return (cellValue/1024)>1024?((cellValue/1024/1024).toFixed(2)+"M"):((cellValue/1024).toFixed(2)+"K")
            }
        },        
        computed: {
            ...mapGetters({
                taskInfo: 'getTaskInfo',
                taskResource: 'getTaskResource',
                taskFile:'getTaskFile',
                isLogin: 'getLoginState',
                taskStatics:'getTaskStatics'
            })
        },
        created() {
            this.$store.dispatch('queryTaskInfo',{"taskId":this.$route.params.id});
            this.$store.dispatch('queryTaskFile',{"taskId":this.$route.params.id});
            this.$store.dispatch('queryTaskResource',{"taskId":this.$route.params.id});
            this.$store.dispatch('queryTaskStatistics',{"taskId":this.$route.params.id});
        },
		data: function () {
            return {
                dialogVisible: false
				}
			}
    }
</script>

<style>
    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
 .el-form-item{
  margin-bottom:0 !important;
 }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .tableFile,.tableFile td{
      border: 1px solid #dfe6ec;
      border-collapse: collapse;
    }

    .tableFile td{
       padding:10px 36px
    }
    .tableFile td.cell{
       background-color: #eef1f6;
       color: #1f2d3d;
       width:80px;
    }

</style>

