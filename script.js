const followBtn = document.getElementById('followBtn');
let isFollowing = false;

followBtn.addEventListener('click', () => {
  isFollowing = !isFollowing;
  followBtn.textContent = isFollowing ? 'Following' : 'Follow';
});

const themeToggle = document.getElementById('themeToggle');
themeToggle.addEventListener('click', () => {
  document.body.classList.toggle('dark-mode');
});
