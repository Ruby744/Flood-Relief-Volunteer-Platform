document.addEventListener("DOMContentLoaded", () => {
  // Get the current pathname from the URL
  const currentPath = window.location.pathname;

  // Select all nav links
  const navLinks = document.querySelectorAll(".nav-link");

  // Loop through the links and set the active class and aria-current
  navLinks.forEach(link => {
    const linkPath = link.getAttribute("href");

    // Handle the "Admin" link with special cases
    if (linkPath === '/admin' && (currentPath.startsWith('/admin'))) {
      link.classList.add("active");
      link.setAttribute("aria-current", "page");
    }
    // Handle all other links (Home, Volunteer, Donation) based on exact match
    else if (currentPath.endsWith(linkPath)) {
      link.classList.add("active");
      link.setAttribute("aria-current", "page");
    } else {
      link.classList.remove("active");
      link.removeAttribute("aria-current");
    }
  });
});
