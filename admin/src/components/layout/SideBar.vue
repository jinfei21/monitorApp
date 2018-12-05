<template>
    <div class="sidebar">


        <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" theme="dark" router>
<!--
            <el-submenu index="2">
                <template slot="title">
                    <i class="glyphicon  glyphicon-wrench"></i>Deploy管理
                </template>
                <el-menu-item index="/listDeploy">Deploy列表</el-menu-item>
                <el-menu-item index="/addDeploy">添加Deploy</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
                <template slot="title">
                    <i class="glyphicon  el-icon-share"></i>Task管理
                </template>
                <el-menu-item index="/task">Task列表</el-menu-item>

            </el-submenu>
            <el-menu-item index="cleanup">
                <i class="el-icon-date"></i>Cleanup管理
            </el-menu-item>
            <el-submenu index="4">
                <template slot="title"><i class="glyphicon  glyphicon-plane"></i>集群管理
                </template>
                <el-menu-item index="/slave">Slave管理</el-menu-item>
                <el-menu-item index="/rack">Rack管理</el-menu-item>
            </el-submenu>
            <el-submenu index="5">
                <template slot="title"><i class="glyphicon  glyphicon-plane"></i>IPAM管理
                </template>
                <el-menu-item index="/subnet">子网管理</el-menu-item>
                <el-menu-item index="/ip">IP管理</el-menu-item>
            </el-submenu>
            <el-submenu index="6">
                <template slot="title"><i class="glyphicon  glyphicon-plane"></i>事件管理
                </template>
                <el-menu-item index="/event">事件管理</el-menu-item>
                <el-menu-item index="/offset">游标管理</el-menu-item>
            </el-submenu>
            <el-menu-item index="/dashBoard">
                <i class="glyphicon glyphicon-dashboard"></i>集群监控
            </el-menu-item>
            -->
            <el-submenu index="8">
                <template slot="title"><i class="glyphicon  glyphicon-plane"></i>系统管理
                </template>
                <el-menu-item index="/system">系统管理</el-menu-item>
            </el-submenu>
            <el-submenu index="7">
                <template slot="title"><i class="glyphicon  glyphicon-plane"></i>指标管理
                </template>
                <el-menu-item index="/metric">指标管理</el-menu-item>
            </el-submenu>

        </el-menu>
        <!--
     <Menu theme="dark" width="100%" class="menu" :active-name="activeName" :accordion="true">

         <Submenu name="Deploy管理">
             <template slot="title">
                 <i class="fa fa-file"></i>
                 Deploy管理
             </template>
             <Menu-item name="Deploy列表">
                 <router-link to="/listDeploy">
                     Deploy列表
                 </router-link>
             </Menu-item>
             <Menu-item name="添加Deploy">
                 <router-link to="/addDeploy">
                     添加Deploy
                 </router-link>
             </Menu-item>
         </Submenu>
         <Submenu name="DataTable">
             <template slot="title">
                 <i class="fa fa-database"></i>
                 Data Table
             </template>
             <Menu-item name="BasicTable">
                 <router-link to="/basic-table">
                     Basic Table
                 </router-link>
             </Menu-item>
             <Menu-item name="EditableTable">
                 <router-link to="/editable-table">
                     Editable Table
                 </router-link>
             </Menu-item>
         </Submenu>

     </Menu>

          -->
    </div>
</template>
<script>
    export default {
        computed: {
            onRoutes() {
//                return this.$route.path.replace('/home','');
            }
        }
    }
</script>
<style scoped>
    .sidebar {
        display: block;
        position: absolute;
        width: 250px;
        left: 0;
        top: 70px;
        bottom: 0;
        background: #2E363F;
    }

    .sidebar > ul {
        height: 100%;
    }

    .glyphicon {
        margin-right: 10px;
        top: 2px;
    }

</style>
