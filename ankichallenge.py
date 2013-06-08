from aqt import mw
from anki.hooks import addHook
from anki.utils import ids2str
from aqt.utils import tooltip
from aqt.qt import QInputDialog
import httplib2
import urllib
import json
import socket

version = 0
hostport = 'emareaf.dyndns.org:1369'

def ensureName():
    if not mw.col.conf.has_key('ankichallenge-name'):
        name, answered = QInputDialog.getText(
            mw, 'ankichallenge', 'Please chose a name for the Ankichallenge')
        if answered:
            mw.col.conf['ankichallenge-name'] = name

def foo():
    if mw.col.conf.has_key('ankichallenge-name'):
        num = mw.col.db.scalar('select sum(1) from revlog')
        try:
            resp = json.loads(httplib2.Http(timeout=3).request(
                'http://' + hostport + '/points?' +
                urllib.urlencode({'name': mw.col.conf['ankichallenge-name'],
                                  'version': version,
                                  'amount': num}),
                method='POST')[1])
        except (socket.error, httplib2.ServerNotFoundError):
            pass
        else:
            if resp.has_key('msg'):
                tooltip(resp['msg'])

addHook('profileLoaded', ensureName)
addHook('showQuestion', foo)
