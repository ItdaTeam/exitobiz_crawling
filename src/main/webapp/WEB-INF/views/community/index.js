$(document).ready(function(){
    
    var click1 = false; 

    $(".dot").click(function(){

        if(click1 == false){
      
          //활성화가 안된 상태라면
      
          $(".hidddenBox").addClass("on");
      
          click1 = true;
      
        }else{
      
          //활성화가 된 상태라면
      
          $(".hidddenBox").removeClass("on");
      
          click1 = false;
      
        }
      
      
      
      
      });

});