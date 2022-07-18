package artikel.acl
import future.keywords
import data.artikel.acl

default allow = false

pathAccess := {
  "admin": ["read", "readwrite","admin"],
  "readwrite": ["read", "readwrite"],
  "read": ["read"]
}

allow {
  some user in acl.users
  user == token.payload.Username

  some url in acl.urls
  is_allowed_url(url)
}

is_allowed_url(url) {
  url.path == input.path
  url.method == input.method
  url.role in pathAccess[token.payload.Role]
}

token = {"payload": payload} {
  [header, payload, signature] := io.jwt.decode(input.token)
}
