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

function handle_formprofilsubmit(e) {
    if($('#form_profil\\:password').val() === "") {
        $('#erreur-profil').html("Entrez votre mot de passe SVP").show().delay(3000).fadeOut(500);
        e.preventDefault();
    } if($('#form_profil:passwordnew').val() !== "") {
        if($('#form_profil:passwordconfirm').val() === "") {
            $('#erreur-profil').html("Confirmer le nouveau mot de passe SVP").show().delay(3000).fadeOut(500);
            e.preventDefault();
        }else if($('#form_profil:passwordconfirm').val() !== $('#form_profil:passwordconfirm').val()) {
            $('#erreur-profil').html("Le mot de passe de confirmation n'est pas correct").show().delay(3000).fadeOut(500);
            e.preventDefault();
        }
    }
}

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