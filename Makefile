# Documentation-specific stuff.
BRANCH      := $(strip $(shell git branch --contains | grep -v 'no branch' | \
                               head -1 | sed s/'*'//))
DOC_BRANCH  := gh-pages
DOC_DIR     := doc-$(DOC_BRANCH)
REMOTE_NAME := $(shell git for-each-ref --format='%(upstream:short)' refs/heads/$(BRANCH) | cut -d/ -f1)
REMOTE_URL  := $(shell git config --get remote.$(REMOTE_NAME).url)

.PHONY: $(TARGETS) doc

doc: $(DOC_DIR)/index.html

$(DOC_DIR):
	git clone . $@
	cd $@ && git remote rm origin && git remote add origin $(REMOTE_URL) && \
		git fetch && git checkout $(DOC_BRANCH) || \
        (git symbolic-ref HEAD refs/heads/$(DOC_BRANCH) && rm .git/index && \
         git clean -xdf)

target/doc:
	lein with-profile dev doc

$(DOC_DIR)/index.html: target/doc $(DOC_DIR)
	-cd $(DOC_DIR) && git checkout $(DOC_BRANCH) && git fetch && \
		git reset --hard origin/$(DOC_BRANCH) && git rm -rf .
	cp -r $</* $(DOC_DIR)
	rm -rf $<
	-cd $(DOC_DIR) && git add . && git commit -am "Updated docs."
	cd $(DOC_DIR) && git push origin $(DOC_BRANCH)

deploy:
	lein deploy clojars

test: test-coverage

test-coverage:
	lein with-profile dev cloverage --text
	cat target/coverage/coverage.txt

build: test deploy doc

ci4/build: build
