function myColor(){
  var red = document.getElementById('red').value;
  var green = document.getElementById('green').value;
  var blue = document.getElementById('blue').value;
  var visibility = document.getElementById('visibility').value;
  var color = 'rgb(' + red + ',' + green + ',' + blue + ',' + visibility +')';
  document.documentElement.style.setProperty('--blockColor', color);
  document.getElementById('box').value = color
}

function myColorHover(){
  var red = document.getElementById('red-hover').value;
  var green = document.getElementById('green-hover').value;
  var blue = document.getElementById('blue-hover').value;
  var visibility = document.getElementById('visibility-hover').value;
  var color = 'rgba(' + red + ',' + green + ',' + blue + ',' + visibility +')';
  document.documentElement.style.setProperty('--blockColorHover', color);
  document.getElementById('box-hover').value = color
}

myColorHover();
myColor();
document.getElementById('red').addEventListener('input', myColor);
document.getElementById('green').addEventListener('input', myColor);
document.getElementById('blue').addEventListener('input', myColor);
document.getElementById('visibility').addEventListener('input', myColor);
document.getElementById('red-hover').addEventListener('input', myColorHover);
document.getElementById('green-hover').addEventListener('input', myColorHover);
document.getElementById('blue-hover').addEventListener('input', myColorHover);
document.getElementById('visibility-hover').addEventListener('input', myColorHover);
