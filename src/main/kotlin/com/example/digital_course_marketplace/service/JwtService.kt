package com.example.digital_course_marketplace.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap


@Service
class JwtService(@Value("\${jwt.secret-key}") private val secretKey:String) {

    fun generateToken(email: String) : String {
        val claims : MutableMap<String, Any> = HashMap()
        return createToken(claims, email)
    }

    private fun createToken(claims: MutableMap<String,Any>, email:String): String {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis()+1000*60*60*2))
                .setExpiration(Date(System.currentTimeMillis()+7*24*60*60*1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private fun getSignInKey():Key {
        val key = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(key)
    }

    fun extractUsername(token: String):String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token:String , claimsResolver: Function<Claims,T>):T {
        val claims :Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        println("JWT Token: $token")
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .body
    }

}