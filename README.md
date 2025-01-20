# trace

- `app:grpc-spring-server`: https://github.com/grpc-ecosystem/grpc-spring

request

```shell
grpcurl -d '{"user_id":"0101"}' -plaintext localhost:9090 com.example.trace.v1.UserService.GetUser
```