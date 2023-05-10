function myFontSize(){
  var size = document.getElementById('font-size').value;
  document.documentElement.style.setProperty('--fontSizeCoff', size);
}

myFontSize();
document.getElementById('font-size').addEventListener('input', myFontSize);