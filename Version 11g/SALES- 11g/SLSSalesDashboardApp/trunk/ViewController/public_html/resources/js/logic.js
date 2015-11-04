
function getID(event) {
    //alert("hello");
    var component = event.getSource();
    var id = component.getId();
    var myButton = component.findComponent(id);
    var a = "" + myButton;
    var length = a.length * 1;
    var index = a.lastIndexOf("id=") + 3;
    var name = a.substring(index, length);
    //alert(name);
  /*  $("img").elevateZoom( {
        //zoomType : "inner", cursor : "crosshair", zoomWindowFadeIn : 500, zoomWindowFadeOut : 750
        zoomType : "lens", lensShape : "round", lensSize : 200
    });
    
    */
    $(document).ready(function () {
//alert("hello");
    $("img[id='"+name+"']").elevateZoom( {
      // zoomType : "inner", cursor : "crosshair", zoomWindowFadeIn : 500, zoomWindowFadeOut : 750
  //zoomType : "lens", lensShape : "round", lensSize : 200
    
    //tint:true, tintColour:'#F90', tintOpacity:0.5
   scrollZoom : true
    });
});

    
}