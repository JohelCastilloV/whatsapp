db.createCollection( "chat", { capped: true, size: 100000 } );
db.createCollection( "message", { capped: true, size: 100000 } );
db.createCollection( "user");

