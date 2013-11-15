alter table users add constraint unique_user_name unique(userName);
alter table users add constraint unique_email unique(email);
