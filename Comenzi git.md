**git init** \
**git clone** https://github.com/GabrielStoica/WebCrawler.git \
**git remote add origin** https://github.com/GabrielStoica/WebCrawler.git\
**git config --global user.email [e_mail]**\
**git config --global user.name [un_username]**\
**git pull**   = copiaza repository-ul in fisiererle locale\
**git pull origin [nume_branch]**  = copiaza branch-ul [nume_branch] in branch-ul local selectat (ala albastru)\
**git checkout [nume_branch]**   = ma mut pe un anumit branch\
**git status**  = vezi ce fisiere ai modificat față de repository-ul original\
**git add** [nume fisier pe care dorim sa-l includem]\
**git add .**    = adauga toate fisierele/ subfolderele din folderul la commit (in git status cele modificate vor aparea cu verde, adica sunt pregatite sa fie incarcate pe server)\
**git commit -s**   = pregateste un commit (afiseaza editorul text undde scriu descrierea commit-ului); optiunea *-s* include "semnatura" autorului\
**git push**    = uploadeaza commit-ul in repo\


**git branch** issue-1-Implementare-CLI   = creeaza branch nou\


#####====FETCH/ PULL/ MERGE====

**git pull = git fetch + git merge**.
git pull will always merge into the *current branch*

GIT FETCH: You can do a git fetch at any time to update your remote-tracking branches under refs/remotes/<remote>/. This operation never changes any of your own local branches under refs/heads, and is safe to do without changing your working copy.

When you fetch, Git gathers any commits from the target branch that do not exist in your current branch and stores them in your local repository. However, it does not merge them with your current branch.

![uml_secvential](https://i.stack.imgur.com/XwVzT.png)


######**Testare Markdown**

Tabel:
First Header | Second Header
------------ | -------------
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column

Mențiune: @GabrielStoica

Motto:
>*Crawling the web since 2020.*

Task List:
- [x] @VertUnix, #refs, [links](www.google.com), **formatting**, and <del>tags</del> supported
- [x] this is a complete item
- [ ] this is an incomplete item