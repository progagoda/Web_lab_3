function checkHit() {
    let coordinateX = event.pageX - 501;
    let coordinateY = event.pageY - 152;
    let xHidden= document.forms[0].elements[1].value = coordinateX;
    let yHidden = document.forms[0].elements[2].value = coordinateY;

    document.forms[0].elements[14].click();
}
function reformCoord(){
  let x=document.getElementById("form:x_value");
  let y=document.getElementById("form:Y_value");
  let r=document.getElementById("form:R_value");
  document.getElementById("form:x_value").setAttribute("value", x);
  document.getElementById("form:Y_value").setAttribute("value", y);
  document.getElementById("form:R_value").setAttribute("value", r);
}
reformCoord();