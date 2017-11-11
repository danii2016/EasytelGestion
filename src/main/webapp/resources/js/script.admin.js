$(document).ready(function() {
   $(document).on('click', '#start-upload', function() {
      $('#uploadList').fadeOut(500);
      $('#uploadForm').fadeIn(600);
   });
   
   $(document).on('click', '#return-to-list', function() {
      $('#uploadForm').fadeOut(500);
      $('#uploadList').fadeIn(600);
   });
   
   $(document).on('click', '#import-new-file', function() {
        $('#uploadData').fadeOut(500);
        $('#uploadForm').fadeIn(600);
   });
   
   
});

function cancel_importation() {
    $('#uploadData').fadeOut(500);
    $('#uploadForm').fadeIn(600);
}

function finish_importation() {
    $('#btn-final, #image-load').hide();
    $('#btn-finish').show();
}

function postExecuteUpload() {
    $('#uploadForm').hide();
    $('#uploadData').show();
    $('.showData').trigger('click');
}