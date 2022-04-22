module.exports = {
    "transpileDependencies": [
        "vuetify"
    ],

    css: {
        extract: {
            filename: "style.css"
        }
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