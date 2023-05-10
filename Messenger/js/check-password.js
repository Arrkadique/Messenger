function show_hide_password(btn, pswrd){
	var input = document.getElementById(pswrd);
  var button = document.getElementById(btn)
	if (input.getAttribute('type') == 'password') {
    button.src = './img/hide.png';
		input.setAttribute('type', 'text');
	} else {
    button.src = './img/view.png';
		input.setAttribute('type', 'password');
	}

}