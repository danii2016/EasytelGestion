$(document).ready(function() {
   console.log('We are in');
   
});
function postExecuteUpload() {
    $('#uploadForm').hide();
    $('#uploadData').show();
    $('.showData').trigger('click');
}