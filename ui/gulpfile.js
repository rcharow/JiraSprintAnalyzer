/* File: gulpfile.js */

// grab our packages
var gulp       = require('gulp'),
    gutil      = require('gulp-util'),
    jshint     = require('gulp-jshint'),
    sass       = require('gulp-sass'),
    sourcemaps = require('gulp-sourcemaps'),
    concat     = require('gulp-concat');
    merge      = require('merge-stream');

// define the default task and add the watch task to it
gulp.task('default', ['jshint', 'build-js', 'build-vendor', 'build-css']);

// configure the jshint task
gulp.task('jshint', function() {
  return gulp.src('src/main/resources/static/js/**/*.js')
    .pipe(jshint())
    .pipe(jshint.reporter('jshint-stylish'));
});

// configure which files to watch and what tasks to use on file changes
gulp.task('watch', function() {
  gulp.watch('src/main/resources/static/js/**/*.js', ['jshint', 'build-js']);
  gulp.watch('src/main/resources/static/scss/**/*.scss', ['build-css']);
});


//move static js resources to generated resources
gulp.task('build-js', function() {
  return gulp.src('src/main/resources/static/js/**/*.js')
    .pipe(sourcemaps.init())
      .pipe(concat('main.js'))
      //only uglify if gulp is run with '--type production'
      .pipe(gutil.env.type === 'production' ? uglify() : gutil.noop())
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('target/generated-resources/static/js'));
});

//move vendor resources to generated resources
gulp.task('build-vendor', function() {
    var js = gulp.src([
        'node_modules/angular/angular.min.js',
        'node_modules/angular-route/angular-route.min.js',
        'node_modules/jquery/jquery.min.js',
        'node_modules/bootstrap/dist/js/bootstrap.min.js',
        'node_modules/jquery/dist/jquery.min.js'
    ])
    .pipe(gulp.dest('target/generated-resources/static/js'));

    var css = gulp.src([
            'node_modules/bootstrap/dist/css/bootstrap.min.css'
        ])
        .pipe(gulp.dest('target/generated-resources/static/css'));

    return merge(js, css);

});

//compile sass files and move to generated resources
gulp.task('build-css', function(){
  return gulp.src(['src/main/resources/static/scss/**/*.scss'])
     .pipe(sass().on('error', sass.logError))
     .pipe(gulp.dest('target/generated-resources/static/css'));
});