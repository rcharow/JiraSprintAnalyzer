/* File: gulpfile.js */

// grab our packages
var gulp = require('gulp'),
  sass = require('gulp-sass'),
  webpack = require('webpack-stream'),
  sourcemap = require('gulp-sourcemaps'),
  plumber = require('gulp-plumber'),
  watch = require('gulp-watch'),
  karmaServer = require('karma').Server,
  stream = require('merge-stream');



var _output_dir = '../../../../target/classes/static/app/';

// define the default task and add the watch task to it
gulp.task('default', ['fonts', 'copy-sass', 'copy-html', 'pack']);


// configure which files to watch and what tasks to use on file changes
gulp.task('watch', ['sync-html', 'watch-pack'], function () {
  gulp.watch('./assets/scss/**/*.scss', ['copy-sass']);
});

//pack ts to js with webpack
gulp.task('pack', function () {
  return pack('./webpack.config.js');
});

gulp.task('watch-pack', function () {
  pack('./webpack.config.watch.js');
});

gulp.task('fonts', function () {
  return gulp.src('./node_modules/font-awesome/fonts/*')
    .pipe(gulp.dest('./assets/fonts/'));
});

//compile sass files
gulp.task('build-css', ['fonts'], function () {
  var sassStream, cssStream;

  sassStream =  gulp.src('./assets/scss/*.scss')
    .pipe(plumber({errorHandler: handleError}))
    .pipe(sourcemap.init())
    .pipe(sass({
      verbose: true,
      outputStyle: 'compressed',
      includePaths: [
        './node_modules/bootstrap/scss/',
        './node_modules/font-awesome/scss/',
        './node_modules/ng2-toastr/',
        require('bourbon').includePaths

      ],
      sourcemaps: true
    }).on('error', function (err) {
      console.error('\x07'); // so it doesn't just fail (literally) silently!
      sass.logError.bind(this)(err);
    }))
    .pipe(sourcemap.write('./'))
    .pipe(gulp.dest('./assets/css/'));
});


gulp.task('copy-sass', ['build-css'], function () {
  return gulp.src('./assets/css/**/*')
    .pipe(plumber({errorHandler: handleError}))
    .pipe(gulp.dest(_output_dir + '../assets/css'));
});

gulp.task('copy-html', function () {
  return gulp.src('app/**/*.html')
    .pipe(plumber({errorHandler: handleError}))
    .pipe(gulp.dest(_output_dir));
});

gulp.task('sync-html', function () {
  watch('app/**/*.html', {ignoreInitial: false}).pipe(gulp.dest(_output_dir));
});

gulp.task('test', function (done) {
  new karmaServer({configFile: __dirname + '/conf/karma.conf.js'}, done).start();
});

function pack(config) {
  return gulp.src('./app/vendor.js')
    .pipe(plumber({errorHandler: handleError}))
    .pipe(webpack(require(config), require('webpack')))
    .pipe(gulp.dest(_output_dir));
}

function handleError(err) {
  console.log(err.toString());
  this.emit('end');
}