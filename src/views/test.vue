<template>
  <div id="app" @contextmenu="showMenu"
       style="width: 100px;height: 100px;margin-top: 20px;background: red;">
    <vue-context-menu :contextMenuData="contextMenuData"
                      @home="home"
                      @deletedata="deletedata"></vue-context-menu>
  </div>
</template>
<script>
export default {
  name: 'app',
  data () {
    return {
      // 菜单数据
      contextMenuData: {
        menuName: 'demo',
        //菜单显示的位置
        axis: {
          x: null,
          y: null
        },
        //菜单选项
        menulists: [{
          fnHandler: 'home', //绑定事件
          icoName: 'fa fa-home fa-fw', //icon图标
          btnName: '回到主页' //菜单名称
        }, {
          fnHandler: 'deletedata',
          icoName: 'fa fa-minus-square-o  fa-fw',
          btnName: '删除布局'
        }]
      }
    }
  },
  methods: {
      showMenu () {
        event.preventDefault()
        var x = event.clientX
        var y = event.clientY
        // Get the current location
        this.contextMenuData.axis = {
          x, y
        }
      },
      homed () {
        alert("主页")
      },
      deletedata () {
        console.log('delete!')
      }
    }
  }
</script>