var exec = require('cordova/exec');

var Youtube = {
  openYoutube: function(youtubeId ,successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'AdfitYoutube','openYoutube', [youtubeId]);
  }
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.Youtube) {
    window.plugins.Youtube = Youtube;
}

if (module.exports) {
  module.exports = Youtube;
}

