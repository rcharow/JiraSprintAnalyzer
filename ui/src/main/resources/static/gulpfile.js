/* File: gulpfile.js */

// grab our packages
var gulp       = require('gulp'),
    sass       = require('gulp-sass'),
    webpack    = require('webpack-stream');

var _output_dir = '../../../../target/classes/static/app/';

// define the default task and add the watch task to it
gulp.task('default', ['pack', 'build-css']);


// configure which files to watch and what tasks to use on file changes
gulp.task('watch', ['watch-pack'], function() {
  gulp.watch('./scss/**/*.scss', ['build-css']);
});

//pack ts to js with webpack
gulp.task('pack', function () {
    return pack('./webpack.config.js');
});

gulp.task('watch-pack', function () {
    pack('./webpack.config.watch.js');
});

//compile sass files and move to generated resources
gulp.task('build-css', function(){
  return gulp.src(['./scss/**/*.scss'])
     .pipe(sass().on('error', sass.logError))
     .pipe(gulp.dest('./assets/css'));
});

function pack(config) {
    return gulp.src('./app/vendor.js')
        .pipe(webpack(require(config)))
        .pipe(gulp.dest(_output_dir));
}