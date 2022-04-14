# Vue

## vuex

​	Vuex是一个专门为Vue.js应用设计的状态管理架构，统一管理和维护各个vue组件的可变化状态。

​	它有五个核心概念：`state`，`getters`，`mutations`，`actions`，`modules`。

### 代码结构

```vue
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

  },
  getters: {

  },
  mutations: {

  },
  actions: {

  },
  modules: {
    
  }
})

```



### 概述

state  ---> 基本数据

getters ---> 从基本数据派生的数据

mutations ---> 提交更改数据的方法，同步！

actions ---> 像一个装饰器

modules ---> 模块化vuex

[资料]([Vuex的五个属性 - 简书 (jianshu.com)](https://www.jianshu.com/p/dd8a1507d9e7))

### mapState

用于避免冗余

``` vue
computed:{
    ...mapState({
        count: 'count',  // string    映射 this.count 为 store.state.count的值
        // 箭头函数可使代码更简练
        name: (state) => state.name, // function   映射 this.name 为 store.state.name的值
        nameAlias: 'name', // string   映射 this.nameAlias 为 store.state.name的值
        countplustempcount: function (state) { // 用普通函数this指向vue实例,但是在箭头函数中this就不是指向vue实例了，所以这里必须用普通哈数
          return this.tempcount + state.count
        },
        countplustempcount2 (state) {
          return this.tempcount2 + state.count
        } 
    })
}

```

