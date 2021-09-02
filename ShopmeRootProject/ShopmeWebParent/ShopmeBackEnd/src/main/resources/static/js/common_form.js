
$(document).ready(function () {
			
			$('.myPhoto').css("height", 180); // Units are assumed to be pixels
			$('.myPhoto').css("width", 160);
			$('.myPhoto').css('object-fit','cover');
			
			
			// Cancel Button Click  -> Redirect in /users
			$("#cancelBtn").on("click", function () {
				window.location = moduleURL;
				
			});
				
			
			
			// FILE UPLOAD
			$("#fileImage").change(function () {
				fileSize = this.files[0].size;
				
				// old size -> if(fileSize > 1048576 ){	// 1024 x 1024 = 1048576
				
				if(fileSize > 102400 ){	
					this.setCustomValidity("Image must be less than 100KB !!");
					this.reportValidity();
					
				} 
				else {
						this.setCustomValidity("");
							
						$('.myPhoto').css("height", 180); // Units are assumed to be pixels
						$('.myPhoto').css("width", 160);
						$('.myPhoto').css('object-fit','cover');
						
						
						showImageThumbnail(this);
				}
				
			
				
			});
		});
	
		// FILE UPLOAD
		function showImageThumbnail(fileInput) {
		var file = fileInput.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#thumbnail").attr("src",e.target.result);
		}
		
		reader.readAsDataURL(file);
	}
	