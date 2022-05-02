module.exports = {
    "transpileDependencies": [
        "vuetify"
    ],

    css: {
        extract: {
            filename: "style.css"
        }
    },
    devServer: {
        port: 443 // 此处修改你想要的端口号
    }
    // devServer: {
    //     '/api': {
    //         traget: 'http://localhost:8081',
    //         ws: true,
    //         changeOrigin: true,
    //         pathRewrite: {
    //             '^/api': ''
    //         }
    //     }
    // }
}