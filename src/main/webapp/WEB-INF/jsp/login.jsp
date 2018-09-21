<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />

Login Sample
[${session_user.username}]
<form name="form" class="login-form" method="POST" action="/login">
  <input type ="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/> 
  <div class="row">
    <div class="input-field col s12">
      <label for="email">Email</label>
      <input id="email" type="email" name="username" class="validate"/>
    </div>
  </div>
  <div class="row">
    <div class="input-field col s12">
      <label for="password">Password</label>
      <input id="password" type="password" name="password" class="validate"/>
    </div>
  </div>
  
  <button type="submit">Log In</button>&nbsp;&nbsp;&nbsp;<br>
  <a href="javascript:doLogin()"> To Admin page</a><br><br><br>
  
  Token : <span id="quickstart-oauthtoken"></span><br>
  Name : <span id="quickstart-name"></span><br>
  Email : <span id="quickstart-email"></span><br>
  <input type="button" id="quickstart-sign-ing" onclick="doGoogleLogin()" value="For Google"/>
  <input type="button" id="quickstart-sign-out" onclick="doGoogleLogout()" value="For Google(Sign out)"/>
  <br><br>
  <input type="button" id="quickstart-sign-ing" onclick="doFacebookLogin()" value="For Facebook"/>
  <input type="button" id="quickstart-sign-out" onclick="doFacebookLogout()" value="For Facebook(Sign out)"/>
</form>
<iframe id="hiddenFrm" style="display:none;"></iframe>


<script src="${ contextPath }/resources/js/jquery/jquery.min.1.7.2.js"></script>
<script type="text/javascript">
var contextPath = "${ contextPath }";
function doLogin(){

	var params =  {};
	params.username=$("#email").val();
	params.password=$("#password").val();
	
	console.log(params);
	
	$.ajax({
        url:contextPath+'/login',
        type:'POST',
        data: params,
        success:function(data){
        	console.log(data);
    		location.href=contextPath+"/admin/";
        },
        error:function(jqXHR, textStatus, errorThrown){
        	alert("아이디 또는 비밀번호가 존재하지 않습니다.");
        	printError('에러 발생~~ \n' + textStatus + ' : ' + errorThrown)
        }
    });
}

function printError(msg){
	console.log(msg);
}
</script>


<script src="https://www.gstatic.com/firebasejs/4.13.0/firebase.js"></script>
<script>
	var firebaseApiKey="AIzaSyByBX__MnhGBczxeoiJk4DTyI6AZTZvXnU";
	var firebaseProjectId="springboottest-ccaf2";
	var firebaseDatabaseName="springboottest-ccaf2";
	var firebaseBucket="springboottest-ccaf2";
	var firebaseSenderId="167767696032";
	
	var config = {
		apiKey: firebaseApiKey,
		authDomain: firebaseProjectId+".firebaseapp.com",
		databaseURL: "https://"+firebaseDatabaseName+".firebaseio.com",
		projectId: firebaseProjectId,
		storageBucket: firebaseBucket+".appspot.com",
		messagingSenderId: firebaseSenderId
	};
	
	firebase.initializeApp(config);
	
</script>
<script>
	function doGoogleLogin(){
		var user = firebase.auth().currentUser;
	
		if (user) {
			console.log("User is signed in.");
			document.getElementById('quickstart-name').textContent = user.displayName;
			document.getElementById('quickstart-email').textContent = user.email;
			document.getElementById('quickstart-oauthtoken').textContent = "";
		} else {
			console.log("No user is signed in.");
			var provider = new firebase.auth.GoogleAuthProvider();
			provider.addScope('https://www.googleapis.com/auth/plus.login');
			firebase.auth().setPersistence(firebase.auth.Auth.Persistence.SESSION)
			.then(function() {
			return firebase.auth().signInWithPopup(provider).then(function(result) {
					var token = result.credential.accessToken;
					var user = result.user;
					console.log(user);
					document.getElementById('quickstart-name').textContent = user.displayName;
					document.getElementById('quickstart-email').textContent = user.email;
					document.getElementById('quickstart-oauthtoken').textContent = token;
				}).catch(function(error) {
					var errorCode = error.code;
					var errorMessage = error.message;
					var email = error.email;
					var credential = error.credential; 
					if (errorCode === 'auth/account-exists-with-different-credential') {
						alert("You have already signed up with a different auth provider for that email.");
					} else if (errorCode === 'auth/auth-domain-config-required') {
						alert("An auth domain configuration is required"); 
					} else if (errorCode === 'auth/cancelled-popup-request') {
						alert("Popup Google sign in was canceled");
					} else if (errorCode === 'auth/operation-not-allowed') {
						alert("Operation is not allowed");
					} else if (errorCode === 'auth/operation-not-supported-in-this-environment') {
						alert("Operation is not supported in this environment");
					} else if (errorCode === 'auth/popup-blocked') {
						alert("Sign in popup got blocked");
					} else if (errorCode === 'auth/popup-closed-by-user') {
						alert("Google sign in popup got cancelled");
					} else if (errorCode === 'auth/unauthorized-domain') {
						alert("Unauthorized domain");
					} else {
						console.error(error);
					}
				});
			})
			.catch(function(error) {
				console.log(error);
				var errorCode = error.code;
				var errorMessage = error.message;
			});
		}
	}
	
	
	function doGoogleLogout() {
		firebase.auth().signOut().then(function() {
			console.log("Logout successful");
			var token = document.getElementById('quickstart-name').textContent;
			
			document.getElementById('quickstart-name').textContent = "";
			document.getElementById('quickstart-email').textContent = "";
			document.getElementById('quickstart-oauthtoken').textContent = "";
			
			hiddenFrm.src="https://accounts.google.com/Logout?hl=ko&amp;continue=https://www.google.com/%3Fauthuser%3D0&amp;timeStmp=1524623136&amp;secTok="+token;
		}, function(error) {
			console.log(error);
		});
	}
</script>

<script>
function doFacebookLogin(){
	var user = firebase.auth().currentUser;

	if (user) {
		console.log("User is signed in.");
		document.getElementById('quickstart-name').textContent = user.displayName;
		document.getElementById('quickstart-email').textContent = user.email;
		document.getElementById('quickstart-oauthtoken').textContent = "";
	} else {
		console.log("No user is signed in.");
		var provider = new firebase.auth.FacebookAuthProvider();
		firebase.auth().setPersistence(firebase.auth.Auth.Persistence.SESSION)
		.then(function() {
		return firebase.auth().signInWithPopup(provider).then(function(result) {
				var token = result.credential.accessToken;
				var user = result.user;
				console.log(user);
				document.getElementById('quickstart-name').textContent = user.displayName;
				document.getElementById('quickstart-email').textContent = user.email;
				document.getElementById('quickstart-oauthtoken').textContent = token;
			}).catch(function(error) {
				var errorCode = error.code;
				var errorMessage = error.message;
				var email = error.email;
				var credential = error.credential; 
				if (errorCode === 'auth/account-exists-with-different-credential') {
					alert("You have already signed up with a different auth provider for that email.");
				} else if (errorCode === 'auth/auth-domain-config-required') {
					alert("An auth domain configuration is required"); 
				} else if (errorCode === 'auth/cancelled-popup-request') {
					alert("Popup Google sign in was canceled");
				} else if (errorCode === 'auth/operation-not-allowed') {
					alert("Operation is not allowed");
				} else if (errorCode === 'auth/operation-not-supported-in-this-environment') {
					alert("Operation is not supported in this environment");
				} else if (errorCode === 'auth/popup-blocked') {
					alert("Sign in popup got blocked");
				} else if (errorCode === 'auth/popup-closed-by-user') {
					alert("Facebook sign in popup got cancelled");
				} else if (errorCode === 'auth/unauthorized-domain') {
					alert("Unauthorized domain");
				} else {
					console.error(error);
				}
			});
		})
		.catch(function(error) {
			console.log(error);
			var errorCode = error.code;
			var errorMessage = error.message;
		});
	}
}


function doFacebookLogout() {
	firebase.auth().signOut().then(function() {
		console.log("Logout successful");
		var token = document.getElementById('quickstart-name').textContent;
		
		document.getElementById('quickstart-name').textContent = "";
		document.getElementById('quickstart-email').textContent = "";
		document.getElementById('quickstart-oauthtoken').textContent = "";
	}, function(error) {
		console.log(error);
	});
}
</script>