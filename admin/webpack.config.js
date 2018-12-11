const path = require('path');
const resolve = require('path').resolve;
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const url = require('url');
const publicPath = '';
const request = require("request");

module.exports = (options = {}) => ({
    entry: {
        vendor: './src/vendor.js',
        index: './src/main.js'
    },
    output: {
        path: resolve(__dirname, '../gate/src/main/resources/static'),
        filename: options.dev ? '[name].js' : '[name].js?[chunkhash]',
        chunkFilename: '[id].js?[chunkhash]',
        publicPath: options.dev ? '/assets/' : publicPath
    },
    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            }
        ],
        rules: [
            {
                test: /\.vue$/,
                use: ['vue-loader']
            },
            {
                test: /\.js$/,
                use: ['babel-loader'],
                include: [resolve('src'), resolve('test')],
                exclude: /node_modules/
            },
            {
                test: /\.less$/,
                use: [{
                    loader: "style-loader" // creates style nodes from JS strings
                }, {
                    loader: "css-loader" // translates CSS into CommonJS
                }, {
                    loader: "less-loader" // compiles Less to CSS
                }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader', 'postcss-loader']
            },
            {
                test: /\.(png|jpg|jpeg|gif|eot|ttf|woff|woff2|svg|svgz)(\?.+)?$/,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 10000
                    }
                }]
            }
        ]
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin({
            names: ['vendor', 'manifest']
        }),
        new HtmlWebpackPlugin({
            title: 'Galaxy App',
            template: 'src/index.html'
        })
    ],
    resolve: {
        extensions: ['.js', '.vue', '.json'],
        alias: {
            '~': resolve(__dirname, 'src'),
            'vue': 'vue/dist/vue.js',
        }
    },
    devServer: {
        port: 8081,
        contentBase: [path.join(__dirname, "public"), path.join(__dirname, "/assets")],
        proxy: {
            '/api/': {
                target: 'http://localhost:8089',
                changeOrigin: true,
            }
        },
        historyApiFallback: {
            index: url.parse(options.dev ? '/assets/' : publicPath).pathname
        },
        setup(app){
            app.get('/some/path', function(req, res) {
                res.json({ custom: 'response' });
            });
        }
    },
    devtool: options.dev ? '#eval-source-map' : '#source-map'
})
