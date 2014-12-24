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
        files: ['assets/sass/*.scss'],
        tasks: ['sass','cssmin']
      },
      html: {
        files: 'assets/html/*.html',
        tasks: ['includes']
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
    },
    includes: {
      files: {
        src: 'assets/html/*.html', // Source files
        dest: 'static/html/', // Destination directory
        flatten: true,
        options: {
          silent: true
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-includes');

  grunt.registerTask('default',['connect','sass', 'cssmin', 'includes', 'watch']);

};
