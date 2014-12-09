module.exports = function (grunt) {
  grunt.initConfig({
    connect: {
      server: {
        options: {
          port: 8000
        }
      }
    },
    watch: {
      css: {
        files: 'assets/sass/*.scss',
        tasks: ['sass','cssmin']
      }
    },
    sass: {
      dist: {
        files: {
          'assets/css/styles.css' : 'assets/sass/styles.scss',
        }
      }
    },
    cssmin: {
      combine: {
        files: {
          'static/css/styles_min.css': 'assets/css/*.css',
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-connect');

  grunt.registerTask('default',['connect','sass', 'cssmin', 'watch']);

};
