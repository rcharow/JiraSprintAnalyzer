var webpack = require("webpack");
var helpers = require("./conf/helpers");

var _output_dir = '../../../../target/classes/static/app/';
module.exports = {
  cache: true,
  devtool: 'source-map',
  entry: {
    app: './app/main.ts',
    'third-party': './app/lib/vendor.ts'
  },
  resolve: {
    extensions: ['.webpack.js', '.web.js', '.ts', '.tsx', '.js']
  },
  output: {
    filename: _output_dir + '[name].js'
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        exclude: /node_modules/,
        loaders: [
          {
            loader: 'awesome-typescript-loader',
            options: {configFileName: helpers.root('.', 'tsconfig.json')}
          }
        ]
      }
    ]
  },
  plugins: [
    new webpack.ContextReplacementPlugin(
      /angular(\\|\/)core(\\|\/)@angular/,
      helpers.root('.', 'fake/') //fake directory so it doesn't watch the whole tree during the `watch` dev process.
    ),
    new webpack.optimize.CommonsChunkPlugin({name: 'third-party', filename: _output_dir + 'third-party.js'}),
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery',
      'Tether': 'tether'
    })]
};