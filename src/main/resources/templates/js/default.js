$(document).ready(function(){

    $(".all").on("click",function(){
        $(".nav").addClass("on");
        $(".dim").show();
    });
    $(".nav .close").on("click",function(){
        $(".nav").removeClass("on");
        $(".dim").hide();
    });
                  
});