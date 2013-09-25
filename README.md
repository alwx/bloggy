# Bloggy

Hello. Bloggy is a small & simple Clojure-powered blog. It uses [Ring](https://github.com/ring-clojure/ring), [Compojure](https://github.com/weavejester/compojure), [Friend](https://github.com/cemerick/friend), [Enlive](https://github.com/cgrand/enlive), [Korma](http://sqlkorma.com/), [markdown-clj](https://github.com/yogthos/markdown-clj) & SQLite as main database.

It may be used as a demonstration. Or, of course, you may install this blog on your server.
Personally I recommend to use [Disqus](http://disqus.com) to add comments for your posts (`see templates/page_blog_post.html`).

## Usage

* Git clone;
* Run server using `lein run`;
* Visit `http://your-host.com/blog-add` to add your first blog post (use login `user` and password `secret`).

## License

Copyright © 2013 alwx

Distributed under the Eclipse Public License, the same as Clojure.
