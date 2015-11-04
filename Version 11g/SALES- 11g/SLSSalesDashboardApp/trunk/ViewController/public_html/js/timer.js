/* Timer JS class */

/* Javascript code courtesy of http://www.w3schools.com */

function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    var a="AM";
    var b=h;
   
    // add a zero in front of numbers<10
    m = checkTime(m);
    s = checkTime(s);
    h= checkHour(h);
    a= checkAmPm(a,b);
    document.getElementById('txt').innerHTML = h + ":" + m +' '+' '+a;
    
    t = setTimeout('startTime()', 500);
}
function checkAmPm(r,s){
    if(s>12){
        r="PM";
    }
    else{
        r="AM";
    }
    return(r);
}
function checkHour(p){
    if(p>12){
    p=(p-12);
    
    }
    else{
        p=p;
        
    }
    return p;
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}