var webpack = require("webpack");

var _output_dir = '../../../../target/classes/static/app/';
module.exports = {
    _output_dir: _output_dir,
    cache: true,
    devtool: 'source-map',
    entry: {
        app: './app/main.ts',
        poly: './app/lib/polyfill.ts',
        'third-party': './app/lib/vendor.ts'
    },
    resolve: {
        extensions: ['', '.webpack.js', '.web.js', '.ts', '.tsx', '.js']
    },
    output: {
        filename: _output_dir + '[name].js'
    },
    module: {
        loaders: [
            {test: /\.tsx?$/, loader: 'ts-loader'}
        ]
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('third-party', _output_dir + 'third-party.js'),
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        })]
};