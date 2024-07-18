<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title>CMC-SWP</title>
		<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
		<meta name="keywords" content="CMC-SWP" />
		<meta name="description" content="CMC-SWP">
		<meta name="author" content="Aashdit Technologies">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<link rel="shortcut icon" href="${contextPath}/loginPage/images/favicon.png">
		
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="cache-control" content="no-store" />
		<meta http-equiv="cache-control" content="pre-check=0" />
		<meta http-equiv="cache-control" content="post-check=0" />
		<meta http-equiv="cache-control" content="must-revalidate" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="pragma" content="no-cache" />

		<link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
		<link rel="stylesheet" href="${contextPath}/assets/css/fonts.min.css" />

		<!-- CSS Files -->
		<link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="${contextPath}/assets/css/atlantis.css">
		<link rel="stylesheet" href="${contextPath}/assets/css/custom.css">
		<link rel="stylesheet" href="${contextPath}/assets/css/jbox.css">

		<link rel="stylesheet" href="${contextPath}/assets/vendor/select2full/select2.min.css" />
		<link rel="stylesheet" href="${contextPath}/assets/vendor/select2full/select2-bootstrap.css" />
		<link href="${contextPath}/assets/vendor/jquery_datepicker/jquery.datepick.css" rel="stylesheet">
		<link href="${contextPath}/assets/vendor/datatables/datatables.min.css" rel="stylesheet">
		<link rel="stylesheet" href="${contextPath}/assets/appJs/admin/multiselect/bootstrap-select.min.css">

		<script src="${contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
		<script src="${contextPath}/assets/js/parsley.min.js"></script>
	</head>
	<body>
	<div class="main-header">
		<!-- Logo Header -->
		<div class="logo-header" data-background-color="blue">
			<a href="${contextPath}/privacyPolicy" class="logo logoinnertext"></a>
		</div>
		<!-- End Logo Header -->

		<!-- Navbar Header -->
		<nav class="navbar navbar-header navbar-expand-lg"
			data-background-color="white">

			<div class="container-fluid">
				<a href="${contextPath}/home" class="logo"> <img
					src="${contextPath}/loginPage/images/logo.png" alt="navbar brand"
					class="navbar-brand">
				</a>
				<div class="nav-item nav-text">
					<h1>EXECUTIVE DASHBOARD</h1>
					<b></b>
				</div>

				<ul class="navbar-nav topbar-nav ml-md-auto align-items-center">

				</ul>
			</div>
		</nav>
		<!-- End Navbar -->
	</div>


	<div class="content">
		<div class="page-inner">
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-12">
					<div class="card card-outer">
						<div class="card-header">
							<h4 class="card-title">Privacy Policy</h4>
						</div>
						<div class="card-body">
							<p>This page is used to inform visitors regarding our
								policies with the collection, use, and disclosure of Personal
								Information if anyone decided to use our Service.</p>
							<p>If you choose to use our Service, then you agree to the
								collection and use of information in relation to this policy.
								The Personal Information that we collect is used for providing
								and improving the Service. We will not use or share your
								information with anyone except as described in this Privacy
								Policy.</p>
							<p>The terms used in this Privacy Policy have the same
								meanings as in our Terms and Conditions, which is accessible at
								Govt. of Odisha unless otherwise defined in this Privacy Policy.
							</p>
							<p>
								<strong>Information Collection and Use</strong>
							</p>
							<p>
								For a better experience, while using our Service, we may require
								you to provide us with certain personally identifiable
								information, including but not limited to <a
									href="https://odishalivelihoodsmission.in/">https://odishalivelihoodsmission.in</a>.
								The information that we request will be retained by us and used
								as described in this privacy policy.
							</p>
							<div>
								<p>The app does use third party services that may collect
									information used to identify you.</p>
								<p>Link to privacy policy of third party service providers
									used by the app</p>
								<ul>
									<li><a href="https://www.google.com/policies/privacy/"
										target="_blank" rel="noopener noreferrer">Google Play
											Services</a></li>
								</ul>
							</div>
							<p>
								<strong>Log Data</strong>
							</p>
							<p>We want to inform you that whenever you use our Service,
								in a case of an error in the app we collect data and information
								(through third party products) on your phone called Log Data.
								This Log Data may include information such as your device
								Internet Protocol address, device name, operating system
								version, the configuration of the app when utilizing our
								Service, the time and date of your use of the Service, and other
								statistics.</p>
							<p>
								<strong>Cookies</strong>
							</p>
							<p>Cookies are files with a small amount of data that are
								commonly used as anonymous unique identifiers. These are sent to
								your browser from the websites that you visit and are stored on
								your device's internal memory.</p>
							<p>This Service does not use these cookies explicitly.
								However, the app may use third party code and libraries that use
								cookies to collect information and improve their services. You
								have the option to either accept or refuse these cookies and
								know when a cookie is being sent to your device. If you choose
								to refuse our cookies, you may not be able to use some portions
								of this Service.</p>
							<p>
								<strong>Service Providers</strong>
							</p>
							<p>We may employ third-party companies and individuals due to
								the following reasons:</p>
							<ul>
								<li>To facilitate our Service;</li>
								<li>To provide the Service on our behalf;</li>
								<li>To perform Service-related services; or</li>
								<li>To assist us in analyzing how our Service is used.</li>
							</ul>
							<p>We want to inform users of this Service that these third
								parties have access to your Personal Information. The reason is
								to perform the tasks assigned to them on our behalf. However,
								they are obligated not to disclose or use the information for
								any other purpose.</p>
							<p>
								<strong>Security</strong>
							</p>
							<p>We value your trust in providing us your Personal
								Information, thus we are striving to use commercially acceptable
								means of protecting it. But remember that no method of
								transmission over the internet, or method of electronic storage
								is 100% secure and reliable, and we cannot guarantee its
								absolute security.</p>
							<p>
								<strong>Links to Other Sites</strong>
							</p>
							<p>This Service may contain links to other sites. If you
								click on a third-party link, you will be directed to that site.
								Note that these external sites are not operated by us.
								Therefore, we strongly advise you to review the Privacy Policy
								of these websites. We have no control over and assume no
								responsibility for the content, privacy policies, or practices
								of any third-party sites or services.</p>
							<p>
								<strong>Children's Privacy</strong>
							</p>
							<p>These Services do not address anyone under the age of 13.
								We do not knowingly collect personally identifiable information
								from children under 13. In the case we discover that a child
								under 13 has provided us with personal information, we
								immediately delete this from our servers. If you are a parent or
								guardian and you are aware that your child has provided us with
								personal information, please contact us so that we will be able
								to do necessary actions.</p>
							<p>
								<strong>Changes to This Privacy Policy</strong>
							</p>
							<p>We may update our Privacy Policy from time to time. Thus,
								you are advised to review this page periodically for any
								changes. We will notify you of any changes by posting the new
								Privacy Policy on this page.</p>
							<p>This policy is effective as of 21-02-2021</p>
							<p>
								<strong>Contact Us</strong>
							</p>
							<p>If you have any questions or suggestions about our Privacy
								Policy, do not hesitate to contact us at +91 674 2560166 or
								email us at olm.itcell@gmail.com.</p>
							<p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>