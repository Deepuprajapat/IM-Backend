package com.realestate.invest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.Model.Blogs;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Service.BlogsService;
import com.realestate.invest.Service.ProjectService;
import com.realestate.invest.Service.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping("/content")
public class ContentController 
{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BlogsService blogsService;

    @Autowired 
    private PropertyService propertyService;

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/test", produces = "text/html")
    public ResponseEntity<?> getProjectByUrl(@PathVariable String url, HttpServletRequest request) 
    {
        try 
        {
            Project project = this.projectService.findByurl(url);
            if(project != null)
            {
                String uri = request.getRequestURI();
                System.out.println("Url : "+uri);
                String canonicalUrl = "https://investmango.com" + request.getRequestURI();
                String htmlResponse = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>" + project.getMetaTitle() + "</title>" +
                "<meta name=\"description\" content=\"" + project.getMetaDesciption() + "\">" +
                "<meta name=\"keywords\" content=\"" + project.getMetaKeywords() + "\">" +
                "<link rel=\"canonical\" href=\"" + canonicalUrl + "\">" +
                "</head>" +
                "<body>" +
                "<h1>" + project.getMetaTitle() + "</h1><br><br>" +
                "<p>" + project.getMetaDesciption().replace("\n", "<br>") + "</p><br>" +
                "<p><strong>Keywords:</strong> " + project.getMetaKeywords() + "</p><br>" +
                "<p><strong>Canonical URL:</strong> " + canonicalUrl + "</p>" +
                "</body>" +
                "</html>";
                return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    

    @GetMapping(value = "/text", produces = "text/html")
    public ResponseEntity<?> getMetaData(@RequestParam String url, HttpServletRequest request) 
    {
        try 
        {
            String cleanUrl = url.replace("https://investmango.com/", "")
                                .replace("https://www.investmango.com/", "");

            String dynamicValue = "";
            if (cleanUrl.contains("propertyforsale/")) 
            {
                String[] parts = cleanUrl.split("/");
                if (parts.length >= 2) 
                {
                    dynamicValue = parts[1]; 
                    System.out.println("Property Url : "+dynamicValue);
                    Property property = this.propertyService.findByUrl(dynamicValue);
                    if (property == null) 
                    {
                        return new ResponseEntity<>("Property not found with this url: " + url, HttpStatus.NOT_FOUND);
                    }
                    String htmlResponse = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>" + property.getMetaTitle() + "</title>" +
                    "<meta name=\"description\" content=\"" + property.getMetaDescription() + "\">" +
                    "<meta name=\"keywords\" content=\"" + property.getMetaKeywords()+ "\">" +
                    "<link rel=\"canonical\" href=\"" + url + "\">" +
                    "</head>" +
                    "<body>" +
                    "<h1>" + property.getMetaTitle() + "</h1><br><br>" +
                    "<p>" + property.getMetaDescription().replace("\n", "<br>") + "</p><br>" +
                    "<p><strong>Keywords:</strong> " + property.getMetaKeywords() + "</p><br>" +
                    "<p><strong>Canonical URL:</strong> " + url + "</p>" +
                    "</body>" +
                    "</html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
                }
            } 
            else if (cleanUrl.contains("blogs/")) 
            {
                String[] parts = cleanUrl.split("/");
                if (parts.length >= 2) 
                {
                    dynamicValue = parts[1]; 
                    System.out.println("Blog Url : "+dynamicValue);
                    Blogs blogs = this.blogsService.getByUrl(dynamicValue);
                    if (blogs == null) throw new Exception("Blogs not found with this url : " + url);
                    String htmlResponse = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>" + blogs.getMetaTitle() + "</title>" +
                    "<meta name=\"description\" content=\"" + blogs.getDescription() + "\">" +
                    "<meta name=\"keywords\" content=\"" + blogs.getMetakeywords() + "\">" +
                    "<link rel=\"canonical\" href=\"" + url + "\">" +
                    "</head>" +
                    "<body>" +
                    "<h1>" + blogs.getMetaTitle() + "</h1><br><br>" +
                    "<p>" + blogs.getDescription().replace("\n", "<br>") + "</p><br>" +
                    "<p><strong>Keywords:</strong> " + blogs.getMetakeywords() + "</p><br>" +
                    "<p><strong>Canonical URL:</strong> " + url + "</p>" +
                    "</body>" +
                    "</html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
                }
            } 
            else 
            {
                dynamicValue = cleanUrl.split("/")[0];
                
                System.out.println("Property Url else : "+dynamicValue);
                Project project = this.projectService.findByurl(dynamicValue);
                if (project == null) throw new Exception("Property not found with this url : " + url);
                String htmlResponse = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>" + project.getMetaTitle() + "</title>" +
                "<meta name=\"description\" content=\"" + project.getMetaDesciption() + "\">" +
                "<meta name=\"keywords\" content=\"" + project.getMetaKeywords() + "\">" +
                "<link rel=\"canonical\" href=\"" + url + "\">" +
                "</head>" +
                "<body>" +
                "<h1>" + project.getMetaTitle() + "</h1><br><br>" +
                "<p>" + project.getMetaDesciption().replace("\n", "<br>") + "</p><br>" +
                "<p><strong>Keywords:</strong> " + project.getMetaKeywords() + "</p><br>" +
                "<p><strong>Canonical URL:</strong> " + url + "</p>" +
                "</body>" +
                "</html>";
                return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
            }
            return ResponseEntity.ok().body("Success for: " + dynamicValue);
        } 
        catch (Exception e) 
        {
            String htmlError = "<html><body><h1>Error</h1><p>" + e.getMessage() + "</p></body></html>";
            return new ResponseEntity<>(htmlError, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "text/html")
    public ResponseEntity<?> sendMetaData(@RequestParam String url, HttpServletRequest request) 
    {
        try 
        {
            String cleanUrl = url.replace("https://investmango.com/", "")
                                    .replace("https://www.investmango.com/", "")
                                    .replace("www.", "");
            String htmlResponse;
            switch (cleanUrl) 
            {
                case "":
                case "/":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>Real Estate Portfolio &amp; Strategic Management Company</title>" +
                                    "<meta name=\"description\" content=\"Invest Mango: Real estate portfolio and strategic management services. Elevate your financial future through informed decisions and prime opportunities.\">" +
                                    "<meta property=\"og:title\" content=\"Real Estate Portfolio &amp; Strategic Management Company\">" +
                                    "<meta property=\"og:description\" content=\"Invest Mango: Real estate portfolio and strategic management services. Elevate your financial future through informed decisions and prime opportunities.\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" +
                                    "<meta property=\"og:url\" content=\"" +url + "\">" +
                                    "<link rel=\"canonical\" href=\"https://www.investmango.com/\">" 
                                    +"</head><body><h1>Welcome to Invest Mango</h1></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
    
                case "contact":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>Contact Us</title>" +
                                    "<meta name=\"description\" content=\"For more details on On-Site Visits, Locations, Developers' Information, Property Age, Documentation, Bank Assistance, and many more feel free to connect.\">" +
                                    "<meta property=\"og:title\" content=\"Contact Us\">" +
                                    "<meta property=\"og:description\" content=\"For more details on On-Site Visits, Locations, Developers' Information, Property Age, Documentation, Bank Assistance, and many more feel free to connect.\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" +
                                    "<meta property=\"og:url\" content=\"" +url + "\">" +
                                    "<link rel=\"canonical\" href=\"https://www.investmango.com/contact\">" ;
                                    // +"</head><body><h1>Contact Us</h1></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
    
                case "career":
                case "career/":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>Careers | Invest Mango</title>" +
                                    "<meta name=\"description\" content=\"Invest Mango offers a dynamic culture that is all about winning and innovating oneself. Unleash your true potential much more than you have ever imagined.\">" +
                                    "<meta property=\"og:title\" content=\"Careers | Invest Mango\">" +
                                    "<meta property=\"og:description\" content=\"Invest Mango offers a dynamic culture that is all about winning and innovating oneself. Unleash your true potential much more than you have ever imagined.\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" +
                                    "<meta property=\"og:url\" content=\"" +url + "\">" +
                                    "<link rel=\"canonical\" href=\"https://www.investmango.com/career\">" ;
                                    // +"</head><body><h1>Careers at Invest Mango</h1></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
    
                case "about":
                case "about/":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>About Us – Know About Invest Mango</title>" +
                                    "<meta name=\"description\" content=\"Invest Mango | Reputed Investment and Real Estate Portfolio Management Organisation. We provide Real Estate Consulting services in Delhi NCR.\">" +
                                    "<meta name=\"keywords\" content=\"real estate investment consultant, investment and portfolio management, top real estate agents in noida, property investment consultant, commercial property management, real estate consultants services, real estate advisory services, top real estate companies in noida.\">" +
                                    "<meta property=\"og:title\" content=\"About Us – Know About Invest Mango\">" +
                                    "<meta property=\"og:description\" content=\"Invest Mango | Reputed Investment and Real Estate Portfolio Management Organisation. We provide Real Estate Consulting services in Delhi NCR.\">" +
                                    "<meta property=\"og:url\" content=\"" +url + "\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" +
                                    "<link rel=\"canonical\" href=\"https://www.investmango.com/about\">" ;
                                    // "</head><body><h1>About Invest Mango</h1></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
    
                case "sitemap":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                "<title>About Us – Know About Invest Mango</title>" +
                                "<meta property=\"og:title\" content=\"About Us – Know About Invest Mango\">" +
                                "<meta property=\"og:description\" content=\"Invest Mango | Reputed Investment and Real Estate Portfolio Management Organisation. We provide Real Estate Consulting services in Delhi NCR.\">" +
                                "<meta property=\"og:url\" content=\"" +url + "\">" +
                                "<link rel=\"canonical\" href=\"https://www.investmango.com/sitemap\">" ;
                                // + "</head><body><h1>Sitemap</h1></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);

                case "privacy-policy":
                case "privacy-policy/":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                "<title>Privacy Policy | Invest Mango</title>" +
                                "<meta name=\"description\" content=\"Read the privacy policy of Invest Mango to learn how we collect, use, and protect your data.\">" +
                                "<meta property=\"og:title\" content=\"Privacy Policy | Invest Mango\">" +
                                "<meta property=\"og:description\" content=\"Read the privacy policy of Invest Mango to learn how we collect, use, and protect your data.\">" +
                                "<meta name=\"robots\" content=\"index, follow\">" +
                                "<meta property=\"og:url\" content=\"" + url + "\">" +
                                "<link rel=\"canonical\" href=\"https://www.investmango.com/privacy-policy\">" +
                                "</head><body>" +
                                "<div style='max-width: 800px; margin: auto; padding: 40px; font-family: sans-serif; color: #333'>" +
                                "<h1 style='text-align:center;'>Privacy Policy</h1>" +
                                "<p><strong>Effective Date:</strong> 10-05-2025</p>" +

                                "<p style='margin-top: 20px;'>At Invest Mango, we are committed to protecting your privacy and ensuring that your personal information is handled safely and responsibly. This Privacy Policy outlines how we collect, use, disclose, and protect the information you provide to us when you visit our website, use our services, or interact with us in any way.</p>" +

                                "<h2 style='margin-top: 30px;'>1. Information We Collect</h2>" +
                                "<p>We may collect personal information such as your name, email address, phone number, location or address, investment preferences, and any other information you voluntarily provide via forms, chats, or phone calls. In addition, we automatically collect data such as your IP address, browser type and version, device information, pages visited, time spent on the site, and data collected through cookies and tracking technologies.</p>" +

                                "<h2 style='margin-top: 30px;'>2. How We Use Your Information</h2>" +
                                "<p>Your data is used to provide property recommendations and investment options, respond to your inquiries or service requests, send newsletters, updates, and promotional offers (only if you have opted in), improve website performance and user experience, conduct market research and analysis, and comply with legal obligations.</p>" +

                                "<h2 style='margin-top: 30px;'>3. Sharing Your Information</h2>" +
                                "<p>We do not sell your personal information. However, we may share it with trusted developers and real estate partners to facilitate property inquiries, service providers who support our marketing, data analysis, or IT functions, and legal or regulatory authorities when required by law or for legal processes.</p>" +

                                "<h2 style='margin-top: 30px;'>4. Data Security</h2>" +
                                "<p>We implement industry-standard security measures to protect your information from unauthorized access, alteration, disclosure, or destruction. Despite our efforts, no method of online transmission or storage is completely secure.</p>" +

                                "<h2 style='margin-top: 30px;'>5. Cookies & Tracking Technologies</h2>" +
                                "<p>We use cookies to understand user behavior, personalize content, and enable essential website functionalities. You can manage or disable cookies through your browser settings at any time.</p>" +

                                "<h2 style='margin-top: 30px;'>6. Your Rights</h2>" +
                                "<p>You have the right to access, correct, or delete your personal data, opt out of marketing communications, and request a copy of the data we hold about you. To exercise these rights, please contact us at: <a href='mailto:info@investmango.com'>info@investmango.com</a></p>" +

                                "<h2 style='margin-top: 30px;'>7. Third Party Links</h2>" +
                                "<p>Our website may include links to third-party websites. We are not responsible for the privacy practices or content of those sites.</p>" +

                                "<h2 style='margin-top: 30px;'>8. Children’s Privacy</h2>" +
                                "<p>Our services are not intended for children under the age of 18, and we do not knowingly collect personal information from minors.</p>" +

                                "<h2 style='margin-top: 30px;'>9. Changes to this Policy</h2>" +
                                "<p>We may update this Privacy Policy from time to time. Any changes will be posted on this page along with the revised effective date.</p>" +

                                "<h2 style='margin-top: 30px;'>10. Contact Us</h2>" +
                                "<p><strong>Invest Mango</strong><br/>" +
                                "Email: <a href='mailto:info@investmango.com'>info@investmango.com</a><br/>" +
                                "Website: <a href='https://www.investmango.com' target='_blank'>https://www.investmango.com</a></p>" +
                                "</div></body></html>";

                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);

                case "terms-and-conditions":
                case "terms-and-conditions/":
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                "<title>Terms & Conditions | Invest Mango</title>" +
                                "<meta name=\"description\" content=\"Read the Terms & Conditions for using Invest Mango’s website and services.\">" +
                                "<meta property=\"og:title\" content=\"Terms & Conditions | Invest Mango\">" +
                                "<meta property=\"og:description\" content=\"Read the Terms & Conditions for using Invest Mango’s website and services.\">" +
                                "<meta name=\"robots\" content=\"index, follow\">" +
                                "<meta property=\"og:url\" content=\"" + url + "\">" +
                                "<link rel=\"canonical\" href=\"https://www.investmango.com/terms-and-conditions\">" +
                                "</head><body>" +
                                "<div style='max-width: 800px; margin: auto; padding: 40px; font-family: sans-serif; color: #333'>" +
                                "<h1 style='text-align:center;'>Terms of Service</h1>" +
                                "<p><strong>Effective Date:</strong> 10-05-2025</p>" +

                                "<p style='margin-top: 20px;'>Welcome to Invest Mango. By accessing or using our website or services, you agree to comply with and be bound by the following terms and conditions. Please read them carefully.</p>" +

                                "<h2 style='margin-top: 30px;'>1. Acceptance of Terms</h2>" +
                                "<p>By using this website and our services, you agree to these Terms & Conditions and our Privacy Policy. If you do not agree, please refrain from using the site.</p>" +

                                "<h2 style='margin-top: 30px;'>2. Services Offered</h2>" +
                                "<p>Invest Mango is a real estate advisory platform offering:</p>" +
                                "<ul>" +
                                "<li>Property listings (residential & commercial)</li>" +
                                "<li>Investment consultancy</li>" +
                                "<li>Developer tie-ups and project promotions</li>" +
                                "<li>Site visit coordination</li>" +
                                "<li>Pre-launch and resale opportunities</li>" +
                                "</ul>" +
                                "<p>Note: We act as facilitators and not as developers or builders.</p>" +

                                "<h2 style='margin-top: 30px;'>3. User Responsibilities</h2>" +
                                "<p>You agree to:</p>" +
                                "<ul>" +
                                "<li>Provide accurate and complete information during inquiries</li>" +
                                "<li>Use the website for lawful purposes only</li>" +
                                "<li>Refrain from submitting false or misleading information</li>" +
                                "<li>Not copy, reproduce, or misuse any content from the website</li>" +
                                "</ul>" +

                                "<h2 style='margin-top: 30px;'>4. Disclaimer</h2>" +
                                "<ul>" +
                                "<li>We make reasonable efforts to ensure information on our website is accurate, but we do not guarantee its completeness or suitability for any purpose.</li>" +
                                "<li>Real estate pricing, availability, and offers are subject to change without notice.</li>" +
                                "<li>Invest Mango is not liable for decisions made based on our advice or third-party listings.</li>" +
                                "</ul>" +

                                "<h2 style='margin-top: 30px;'>5. Intellectual Property</h2>" +
                                "<p>All website content, including logos, text, images, graphics, and layout, is the property of Invest Mango or its content providers. Unauthorized use is prohibited.</p>" +

                                "<h2 style='margin-top: 30px;'>6. Limitation of Liability</h2>" +
                                "<p>Invest Mango will not be liable for:</p>" +
                                "<ul>" +
                                "<li>Any direct, indirect, incidental, or consequential damage</li>" +
                                "<li>Inaccuracies or omissions in property data</li>" +
                                "<li>Third-party service interruptions or errors</li>" +
                                "<li>Investment losses resulting from reliance on our content</li>" +
                                "</ul>" +

                                "<h2 style='margin-top: 30px;'>7. Third Party Links</h2>" +
                                "<p>Our platform may contain links to third-party websites. We are not responsible for the content, policies, or reliability of those sites.</p>" +

                                "<h2 style='margin-top: 30px;'>8. Termination</h2>" +
                                "<p>We reserve the right to:</p>" +
                                "<ul>" +
                                "<li>Terminate access to our website/services at any time without notice</li>" +
                                "<li>Remove any content that violates our policies or legal obligations</li>" +
                                "</ul>" +

                                "<h2 style='margin-top: 30px;'>9. Changes to Terms</h2>" +
                                "<p>We may update these Terms & Conditions at any time. Updates will be posted on this page and your continued use constitutes acceptance of the new terms.</p>" +

                                "<h2 style='margin-top: 30px;'>10. Governing Law</h2>" +
                                "<p>These Terms shall be governed by and construed in accordance with the laws of India, and any disputes will be subject to the exclusive jurisdiction of courts in Noida, Pune, or Gurugram.</p>" +

                                "<h2 style='margin-top: 30px;'>Contact Us</h2>" +
                                "<p>Email: <a href='mailto:info@investmango.com'>info@investmango.com</a>, <a href='mailto:hr@investmango.com'>hr@investmango.com</a></p>" +

                                "</div></body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);



            }
            String dynamicValue = "";
    
            if (cleanUrl.contains("propertyforsale/")) 
            {
                String[] parts = cleanUrl.split("/");
                if (parts.length >= 2) 
                {
                    dynamicValue = parts[1];
                    Property property = this.propertyService.findByUrl(dynamicValue);
                    if (property == null) throw new Exception("Property not found with this url : " + url);
                    String ogImage = getOgImageFromSchema(property.getProductSchema());
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>" + property.getMetaTitle() + "</title>" +
                                    "<meta name=\"description\" content=\"" + property.getMetaDescription() + "\">" +
                                    "<meta name=\"keywords\" content=\"" + property.getMetaKeywords() + "\">" +
                                    
                                    "<meta property=\"og:title\" content=\"" + property.getMetaTitle() + "\">" +
                                    "<meta property=\"og:description\" content=\"" + property.getMetaDescription() + "\">" +
                                    "<meta property=\"og:image\" content=\"" + ogImage + "\">" +  
                                    "<meta property=\"og:url\" content=\"" + url + "\">" +
                                    "<meta property=\"og:type\" content=\"website\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" +

                                    "<link rel=\"canonical\" href=\"" + url + "\">" +
                                    property.getProductSchema() +
                                    "</head><body><h1>" + property.getMetaTitle() + "</h1>" +
                                    "<p>" + property.getMetaDescription().replace("\n", "<br>") + "</p>" +
                                    "</body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
                }
            } 
            else if (cleanUrl.contains("blogs/")) 
            {
                String[] parts = cleanUrl.split("/");
                if (parts.length >= 2) 
                {
                    dynamicValue = parts[1];
                    Blogs blogs = this.blogsService.getByUrl(dynamicValue);
                    if (blogs == null) throw new Exception("Blogs not found with this url : " + url);
                    String ogImage = this.getOgImageFromSchemas(blogs.getImages());
                    htmlResponse = "<!DOCTYPE html><html><head>" +
                                    "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                    "<title>" + blogs.getMetaTitle() + "</title>" +
                                    "<meta name=\"description\" content=\"" + blogs.getSubHeadings() + "\">" +
                                    "<meta name=\"keywords\" content=\"" + blogs.getMetakeywords() + "\">" +
                                    "<link rel=\"canonical\" href=\"" + url + "\">" +
                                    
                                    "<meta property=\"og:title\" content=\"" + blogs.getMetaTitle() + "\">" +
                                    "<meta property=\"og:description\" content=\"" + blogs.getSubHeadings() + "\">" +
                                    "<meta property=\"og:image\" content=\"" + ogImage + "\">" +  
                                    "<meta property=\"og:url\" content=\"" + url + "\">" +
                                    "<meta property=\"og:type\" content=\"website\">" +
                                    "<meta name=\"robots\" content=\"index, follow\">" + 
                                    blogs.getBlogSchema() +
                                    "</head><body><h1>" + blogs.getMetaTitle() + "</h1>" +
                                    "<p>" + blogs.getSubHeadings().replace("\n", "<br>") + "</p>" +
                                    "</body></html>";
                    return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
                }
            } 
            else 
            {
                dynamicValue = cleanUrl.split("/")[0];
                Project project = this.projectService.findByurl(dynamicValue);
                if (project == null) throw new Exception("Project not found with this url : " + url);
                String ogImage = this.getOgImageFromSchemas(project.getProjectSchema());
                htmlResponse = "<!DOCTYPE html><html><head>" +
                                "<meta name=\"google-site-verification\" content=\"ItwxGLnb2pNSeyJn0kZsRa3DZxRZO3MSCQs5G3kTLgA\">" +
                                "<title>" + project.getMetaTitle() + "</title>" +
                                "<meta name=\"description\" content=\"" + project.getMetaDesciption() + "\">" +
                                "<meta name=\"keywords\" content=\"" + project.getMetaKeywords() + "\">" +
                                
                                "<meta property=\"og:title\" content=\"" + project.getMetaTitle() + "\">" +
                                "<meta property=\"og:description\" content=\"" + project.getMetaDesciption() + "\">" +
                                "<meta property=\"og:image\" content=\"" + ogImage + "\">" +  
                                "<meta property=\"og:url\" content=\"" + url + "\">" +
                                "<meta property=\"og:type\" content=\"website\">" +
                                "<meta name=\"robots\" content=\"index, follow\">" +
                                "<link rel=\"canonical\" href=\"" + url + "\">" +   
                                project.getProjectSchema() +
                                "</head><body><h1>" + project.getMetaTitle() + "</h1>" +
                                "<p>" + project.getMetaDesciption().replace("\n", "<br>") + "</p>" +
                                "</body></html>";
                return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
            }
            return ResponseEntity.ok().body("Success for: " + dynamicValue);
        } 
        catch (Exception e) 
        {
            String htmlError = "<html><body><h1>Error</h1><p>" + e.getMessage() + "</p></body></html>";
            return new ResponseEntity<>(htmlError, HttpStatus.NOT_FOUND);
        }
    }

    public String getOgImageFromSchema(String schemaJson) 
    {
        if (schemaJson == null || schemaJson.isEmpty()) 
        {
            return null;
        }

        try 
        {
            JSONObject schema = new JSONObject(schemaJson);
            if (schema.has("image")) 
            {
                Object image = schema.get("image");

                if (image instanceof String) 
                {
                    return (String) image;
                } 
                else if (image instanceof JSONArray) 
                {
                    JSONArray imageArray = (JSONArray) image;
                    if (imageArray.length() > 0) 
                    {
                        return imageArray.getString(0); 
                    }
                } 
                else if (image instanceof JSONObject) 
                {
                    JSONObject imageObj = (JSONObject) image;
                    if (imageObj.has("url")) 
                    {
                        return imageObj.getString("url"); 
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return null;
    }
    

    public String getOgImageFromSchemas(List<String> schemaList) 
    {
        if (schemaList == null || schemaList.isEmpty()) 
        {
            return null;
        }

        for (String scriptTagContent : schemaList) 
        {
            if (scriptTagContent == null || scriptTagContent.isEmpty()) continue;
            int start = scriptTagContent.indexOf(">");
            int end = scriptTagContent.lastIndexOf("</script>");
            if (start == -1 || end == -1 || start >= end) continue;

            String json = scriptTagContent.substring(start + 1, end).trim();
            if (json.isEmpty()) continue;

            try 
            {
                JSONObject schema = new JSONObject(json);
                if (schema.has("image")) 
                {
                    Object image = schema.get("image");
                    if (image instanceof String) 
                    {
                        return (String) image;
                    } 
                    else if (image instanceof JSONArray) 
                    {
                        JSONArray imageArray = (JSONArray) image;
                        if (imageArray.length() > 0) 
                        {
                            return imageArray.getString(0);
                        }
                    } 
                    else if (image instanceof JSONObject) 
                    {
                        JSONObject imageObj = (JSONObject) image;
                        if (imageObj.has("url")) 
                        {
                            return imageObj.getString("url");
                        }
                    }
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace(); 
            }
        }

        return null; 
    }

}
