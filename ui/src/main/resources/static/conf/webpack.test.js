var webpack = require('webpack');
var helpers = require('./helpers');

module.exports = {
	context: __dirname + '/../',
	devtool: 'inline-source-map',

	resolve: {
		extensions: ['.ts', '.js']
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
		new webpack.ProvidePlugin({
			$: 'jquery',
			jQuery: 'jquery',
			Tether: 'tether'
		})
	]
};
